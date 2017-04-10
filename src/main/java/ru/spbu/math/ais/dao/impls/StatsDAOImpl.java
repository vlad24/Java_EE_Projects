package ru.spbu.math.ais.dao.impls;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.spbu.math.ais.dao.StatsDAO;
import ru.spbu.math.ais.model.Director;
import ru.spbu.math.ais.model.DirectorStatPoint;
import ru.spbu.math.ais.model.StorageGroup;
import ru.spbu.math.ais.model.StorageGroupStatPoint;
import ru.spbu.math.ais.model.TimePoint;
import ru.spbu.math.ais.wrappers.ProblemLink;

/**
 * @author vlad
 * <br>
 * Hibernate-based implementation of StatsDAO @see {@link StatsDAO} <br>
 * Implementation provides basic methods to retrieve observations and calculate several basic statistics upon Directors and Storage Groups.<br>
 * Implementation defines a "problematic" director/storage group in terms of special coefficients(thresholds). <br>
 * Those thresholds are then used to compare entities' states with "normal" states (it is assumed that .
 * The thresholds may be set by user in application configuration file (namely 'appConfig.properties').
 * 
 */
@Repository
public class StatsDAOImpl implements StatsDAO {

	private static final double DEFAULT_DIRECTORS_COEFFCIENT_7      = 100.0;
	private static final double DEFAULT_DIRECTORS_COEFFCIENT_8      = 50.0;
	private static final double DEFAULT_DIRECTORS_COEFFCIENT_9      = 10.0;
	private static final double DEFAULT_STORAGE_GROUPS_COEFFCIENT_5 = 100.0;
	private static final double DEFAULT_STORAGE_GROUPS_COEFFCIENT_6 = 50.0;
	private static final double DEFAULT_STORAGE_GROUPS_COEFFCIENT_7 = 10.0;
	
	@Value("#{appConfig['director.problematic.C7']}")
	private Double directorCoefficient7 = DEFAULT_DIRECTORS_COEFFCIENT_7;
	@Value("#{appConfig['director.problematic.C8']}")
	private Double directorCoefficient8 = DEFAULT_DIRECTORS_COEFFCIENT_8;
	@Value("#{appConfig['director.problematic.C9']}")
	private Double directorCoefficient9 = DEFAULT_DIRECTORS_COEFFCIENT_9;
	@Value("#{appConfig['sg.problematic.G5']}")
	private Double sgCoefficient5       = DEFAULT_STORAGE_GROUPS_COEFFCIENT_5;
	@Value("#{appConfig['sg.problematic.G6']}")
	private Double sgCoefficient6       = DEFAULT_STORAGE_GROUPS_COEFFCIENT_6;
	@Value("#{appConfig['sg.problematic.G7']}")
	private Double sgCoefficient7       = DEFAULT_STORAGE_GROUPS_COEFFCIENT_7;
	

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Cache avg values not to recompute them every time during queries
	 */
	private Map<String, Double> cachedAvgs = new HashMap<String, Double>();

	/**
	 * Returns list of problematic directors based on director's queue values. 
	 * Queue of a director is divided into several buckets(enumerated). The most important are 7th, 8th and 9th buckets (big deviations from 0 indicates overloaded director).  
	 * Therefore, a director D is considered to be problematic if the following predicate is true for director: <br>
	 * <strong> (B<sub>7</sub>(D) > AVG<sub>7</sub> * D_COEFFICIENT<sub>7</sub> ) && <br>
	 *  	    (B<sub>8</sub>(D) > AVG<sub>8</sub> * D_COEFFICIENT<sub>8</sub>) && <br>
	 *  		(B<sub>9</sub>(D) > AVG<sub>9</sub> * D_COEFFICIENT<sub>9</sub>)    <br>
	 * </strong> 
	 * <br>
	 * where B<sub>i</sub>(D) returns number of requests standing in i-th bucket of queue.
	 * AVG<sub>i</sub> is an average of B<sub>i</sub> for all directors. <br> 
	 * Coefficients D_COEFFICIENT_9, D_COEFFICIENT_8, D_COEFFICIENT_7 have default values (100.0, 50.0, 10.0) but may be overridden by in 
	 * <em>appConfig.properties</em> by properties:<br>
	 * <samp>director.problematic.C7, director.problematic.C8, director.problematic.C9 </samp> 
	 * respectively.
	 */
	@Transactional
	public List<Director> getProlematicDirectors() {
		Session session = sessionFactory.getCurrentSession();
		Query selectQuery = session
				.createQuery(new StringBuilder()
						.append("SELECT DISTINCT DT.director FROM Director D")
						.append(" JOIN   D.directorStatPoints DT ")
						.append(" WHERE  DT.bucket7Load > :C7 * :bucket7LoadAvg ")
						.append(" AND    DT.bucket8Load > :C8 * :bucket8LoadAvg ")
						.append(" AND    DT.bucket9Load > :C9 * :bucket9LoadAvg ").toString())
				.setParameter("bucket7LoadAvg", getAvg(DirectorStatPoint.class.getName(), "bucket7Load"))
				.setParameter("bucket8LoadAvg", getAvg(DirectorStatPoint.class.getName(), "bucket8Load"))
				.setParameter("bucket9LoadAvg", getAvg(DirectorStatPoint.class.getName(), "bucket9Load"))
				.setParameter("C7", directorCoefficient7)
				.setParameter("C8", directorCoefficient8)
				.setParameter("C9", directorCoefficient9);
		List<Director> directors = (List<Director>) selectQuery.list();
		return directors;
	}

	/**
	 * Returns list of problematic storage groups(abbreviated to 'SG' further) based on SG's response times. 
	 * An SG has queue buckets for each of which write and read response times are calculated. The most remarkable buckets are 5th, 6th, 7th ones.  
	 * Therefore, an SG S is considered to be problematic if the following predicate is true for S: <br>
	 * <strong>
	 * ( BW<sub>5</sub>(S) + BR<sub>5</sub>(S) > (AVGW<sub>5</sub> + AVGR<sub>5</sub>) * SG_COFFICIENT<sub>5</sub> ) && <br>
	 * ( BW<sub>6</sub>(S) + BR<sub>6</sub>(S) > (AVGW<sub>6</sub> + AVGR<sub>6</sub>) * SG_COFFICIENT<sub>6</sub> ) && <br>
	 * ( BW<sub>7</sub>(S) + BR<sub>7</sub>(S) > (AVGW<sub>7</sub> + AVGR<sub>7</sub>) * SG_COFFICIENT<sub>7</sub> )    <br>
	 * </strong> 
	 * <br>
	 * where BW<sub>i</sub>(S) and BR<sub>i</sub>(S) return response times of a write and read I/O requests standing in i-th bucket respectively.
	 * AVGW<sub>i</sub> (AVGR<sub>i</sub>) is an average of BW<sub>i</sub> and BR<sub>i</sub> for all SGs respectively.<br>
	 * Coefficients SG_COEFFICIENT<sub>7</sub>, SG_COEFFICIENT<sub>6</sub>, SG_COEFFICIENT<sub>5</sub> have default values (100.0, 50.0, 10.0) 
	 * but may be overridden in <em>appConfig.properties</em> by properties:<br>
	 * <samp>sg.problematic.G7, sg.problematic.G6, sg.problematic.G5 </samp> respectively.
	 * */
	@Transactional
	public List<StorageGroup> getProblematicStorageGroups() {
		Session session = sessionFactory.getCurrentSession();
		Query selectQuery = session.createQuery(
					new StringBuilder()
						.append("SELECT DISTINCT SGT.storageGroup FROM StorageGroup SG")
						.append(" JOIN SG.storageGroupStatPoints SGT ")
						.append(" WHERE SGT.bucket5ReadRt > :G5 * (:bucket5ReadRtAvg + :bucket5WriteRtAvg) ")
						.append(" AND   SGT.bucket6ReadRt > :G6 * (:bucket6ReadRtAvg + :bucket6WriteRtAvg) ")
						.append(" AND   SGT.bucket6ReadRt > :G6 * (:bucket6ReadRtAvg + :bucket6WriteRtAvg) ")
						.append(" AND   SGT.bucket7ReadRt > :G7 * (:bucket7ReadRtAvg + :bucket7WriteRtAvg) " ).toString())
				.setParameter("G5", sgCoefficient5)
				.setParameter("G6", sgCoefficient6)
				.setParameter("G7", sgCoefficient7)
				.setParameter("bucket5ReadRtAvg",  getAvg(StorageGroupStatPoint.class.getName(), "bucket5ReadRt"))
				.setParameter("bucket5WriteRtAvg", getAvg(StorageGroupStatPoint.class.getName(), "bucket5WriteRt"))
				.setParameter("bucket6ReadRtAvg",  getAvg(StorageGroupStatPoint.class.getName(), "bucket6ReadRt"))
				.setParameter("bucket6WriteRtAvg", getAvg(StorageGroupStatPoint.class.getName(), "bucket6WriteRt"))
				.setParameter("bucket7ReadRtAvg",  getAvg(StorageGroupStatPoint.class.getName(), "bucket7ReadRt"))
				.setParameter("bucket7WriteRtAvg", getAvg(StorageGroupStatPoint.class.getName(), "bucket7WriteRt"));
		List<StorageGroup> sgs = (List<StorageGroup>) selectQuery.list();
		return sgs;
	}
	
	/**
	 * Establishes potential casual relationship between bad states of storage groups and specified director <br>
	 * A link between a director and a storage group is established if at the time when a director can be called "problematic" 
	 * the storage groups is also "problematic". 
	 * @see ru.spbu.math.ais.dao.StatsDAO#getDirectorAndSGProblemLinks(java.lang.Integer)
	 * @see ru.spbu.math.ais.dao.impls.StatsDAOImpl#getProblematicStorageGroups()
	 * @see ru.spbu.math.ais.dao.impls.StatsDAOImpl#getProblematicDirectors()
	 * 
	 */
	@Transactional
	public List<ProblemLink> getDirectorAndSGProblemLinks(Integer directorId) {
		Session session = sessionFactory.getCurrentSession();
		Query selectQuery = session.createQuery( 
				new StringBuilder()
						.append("SELECT T, TD, TS FROM TimePoint T")
						.append(" JOIN T.storageGroupStatPoints TS")
						.append(" JOIN T.directorStatPoints     TD")
						.append(" WHERE (TD.director.id = :id)")
						.append(" AND   (TD.timePoint = TS.timePoint)")
						.append(" AND   (TD.bucket7Load                       > :D7 * :bucket7LoadAvg)")
						.append(" AND   (TD.bucket8Load                       > :D8 * :bucket8LoadAvg)")
						.append(" AND   (TD.bucket9Load                       > :D9 * :bucket9LoadAvg)")
						.append(" AND   (TS.bucket5ReadRt + TS.bucket5WriteRt > :G5 * (:bucket5ReadRtAvg + :bucket5WriteRtAvg))")
						.append(" AND   (TS.bucket6ReadRt + TS.bucket6WriteRt > :G6 * (:bucket6ReadRtAvg + :bucket6WriteRtAvg))")
						.append(" AND   (TS.bucket7ReadRt + TS.bucket7WriteRt > :G7 * (:bucket7ReadRtAvg + :bucket7WriteRtAvg))").toString())
				.setParameter("id", directorId)
				.setParameter("D7", directorCoefficient7).setParameter("D8", directorCoefficient8).setParameter("D9", directorCoefficient9)
				.setParameter("G5", sgCoefficient5).setParameter("G6", sgCoefficient6).setParameter("G7", sgCoefficient7)
				.setParameter("bucket7LoadAvg",    getAvg(DirectorStatPoint.class.getName(),     "bucket7Load"))
				.setParameter("bucket8LoadAvg",    getAvg(DirectorStatPoint.class.getName(),     "bucket8Load"))
				.setParameter("bucket9LoadAvg",    getAvg(DirectorStatPoint.class.getName(),     "bucket9Load"))
				.setParameter("bucket5ReadRtAvg",  getAvg(StorageGroupStatPoint.class.getName(), "bucket5ReadRt"))
				.setParameter("bucket6ReadRtAvg",  getAvg(StorageGroupStatPoint.class.getName(), "bucket6ReadRt"))
				.setParameter("bucket7ReadRtAvg",  getAvg(StorageGroupStatPoint.class.getName(), "bucket7ReadRt"))
				.setParameter("bucket5WriteRtAvg", getAvg(StorageGroupStatPoint.class.getName(), "bucket5WriteRt"))
				.setParameter("bucket6WriteRtAvg", getAvg(StorageGroupStatPoint.class.getName(), "bucket6WriteRt"))
				.setParameter("bucket7WriteRtAvg", getAvg(StorageGroupStatPoint.class.getName(), "bucket7WriteRt"));
		List<Object[]> resultRows = (List<Object[]>) selectQuery.list();
		List<ProblemLink> problemLinks = new ArrayList<ProblemLink>(resultRows.size());
		for (Object[] row : resultRows) {
			problemLinks.add(new ProblemLink((TimePoint) row[0], (DirectorStatPoint) row[1], (StorageGroupStatPoint) row[2]));
		}
		return problemLinks;
	}
	
	
	/**
	 * @see ru.spbu.math.ais.dao.StatsDAO#getDirectorStatPoints(java.lang.Integer, java.util.Date, java.util.Date)
	 */
	@Transactional
	public List<DirectorStatPoint> getDirectorStatPoints(Integer directorId, Date tFrom, Date tTo) {
		Session session = sessionFactory.getCurrentSession();
		Query selectQuery = session.createQuery(new StringBuilder()
						.append("SELECT S FROM DirectorStatPoint S")
						.append(" WHERE (S.director.id = :id)")
						.append(" AND   (S.timePoint.timestamp >= :tFrom)")
						.append(" AND   (S.timePoint.timestamp < :tTo)   ").toString())
				.setParameter("id",    directorId)
				.setParameter("tFrom", tFrom)
				.setParameter("tTo",   tTo);
		List<DirectorStatPoint> points = (List<DirectorStatPoint>) selectQuery.list();
		return points;
	}

	/**
	 * @see ru.spbu.math.ais.dao.StatsDAO#getMinDateForDirectorsAndSGStats()
	 */
	public Date getMinDateForDirectorsAndSGStats() {
		Session session = sessionFactory.getCurrentSession();
		Query selectQueryFromDirectors = session.createQuery(
				"SELECT MIN(S.timePoint.timestamp) FROM DirectorStatPoint S");
		Query selectQueryFromSGs = session.createQuery(
				"SELECT MIN(S.timePoint.timestamp) FROM StorageGroupStatPoint S");
		Date sgMinPoint       = (Date)selectQueryFromDirectors.list().get(0);
		Date directorMinPoint = (Date)selectQueryFromDirectors.list().get(0);
		return sgMinPoint.compareTo(directorMinPoint) < 0 ? sgMinPoint : directorMinPoint;
	}


	/**
	 * @see ru.spbu.math.ais.dao.StatsDAO#getMaxDateForDirectorsAndSGStats()
	 */
	@Transactional
	public Date getMaxDateForDirectorsAndSGStats() {
		Session session = sessionFactory.getCurrentSession();
		Query selectQueryFromDirectors = session.createQuery(
				"SELECT MAX(S.timePoint.timestamp) FROM DirectorStatPoint S");
		Query selectQueryFromSGs = session.createQuery(
				"SELECT MAX(S.timePoint.timestamp) FROM StorageGroupStatPoint S");
		Date sgMinPoint       = (Date)selectQueryFromSGs.list().get(0);
		Date directorMinPoint = (Date)(selectQueryFromDirectors.list().get(0));
		return sgMinPoint.compareTo(directorMinPoint) > 0 ? sgMinPoint : directorMinPoint;
	}

	/**
	 * @see ru.spbu.math.ais.dao.StatsDAO#getStorageGroupStatPoints(java.lang.Integer, java.util.Date, java.util.Date)
	 */
	@Transactional
	public List<StorageGroupStatPoint> getStorageGroupStatPoints(Integer storageGroupId, Date tFrom, Date tTo) {
		Session session = sessionFactory.getCurrentSession();
		Query selectQuery = session.createQuery(new StringBuilder()
						.append("SELECT S FROM StorageGroupStatPoint S")
						.append(" WHERE (S.storageGroup.id = :id)")
						.append(" AND   (S.timePoint.timestamp >= :tFrom)")
						.append(" AND   (S.timePoint.timestamp <= :tTo)   ").toString())
				.setParameter("id",    storageGroupId)
				.setParameter("tFrom", tFrom)
				.setParameter("tTo",   tTo);
		List<StorageGroupStatPoint> points = (List<StorageGroupStatPoint>) selectQuery.list();
		return points;
	}

	
	/**
	 * Calculates average value of attribute values of entity
	 * Tries to get cached value of average of entitie's attribute and if fails - computes it.
	 * @param entitiyName name of entity (e.g. Director)
	 * @param attribute name of attribute (e.g. bucket7Load)
	 * @return
	 */
	@Transactional
	private Double getAvg(String entitiyName, String attribute) {
		/*
		 * Database does not change in our case => can cache.
		 * Otherwise could look if the database has been changed 
		 */
		if (cachedAvgs.containsKey(attribute)) {
			return cachedAvgs.get(attribute);
		} else {
			return calculateAvg(entitiyName, attribute);
		}
	}

	/**
	 * Calculates average value of attribute values of entity
	 * @param entity name of entity (e.g. Director)
	 * @param attribute name of attribute to calculate average over
	 * @return average value
	 */
	@SuppressWarnings("rawtypes")
	private Double calculateAvg(String entity, String attribute) {
		Session session = sessionFactory.getCurrentSession();
		Query selectQuery = session.createQuery("SELECT avg(R." + attribute + ") FROM " + entity + " R");
		Double avg = Double.valueOf((selectQuery.list().get(0)).toString());
		cachedAvgs.put(attribute, avg);
		return avg;
	}

}
