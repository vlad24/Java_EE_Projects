package ru.spbu.math.ais.dao;

import java.util.Date;
import java.util.List;

import ru.spbu.math.ais.model.Director;
import ru.spbu.math.ais.model.DirectorStatPoint;
import ru.spbu.math.ais.model.StorageGroup;
import ru.spbu.math.ais.model.StorageGroupStatPoint;
import ru.spbu.math.ais.wrappers.ProblemLink;

/**
 * @author vlad 
 * 
 * <br>
 * Statistic information DAO. 
 * Aimed to retrieve statistic observations upon Directors and Storage Groups and make different conclusions based on observations.
 * One of the key point of the DAO is ability to distinguish statistically bad states of entities in order
 *  to build probable casual relationship between them.
 */
public interface StatsDAO {
	
	/**
	 * Fetch all problematic directors. What a "problematic" director is defined at implementation level.
	 * @return list of "problematic" directors
	 */
	List<Director>  getProlematicDirectors();
	
	/**
	 * Fetch all problematic storage groups based on statistics. What a "problematic" storageGroup is defined at implementation level
	 * @return list of "problematic" storageGroups
	 */
	List<StorageGroup> getProblematicStorageGroups();
	
	/**
	 * Get statistic observations for a specific director in specified time interval.
	 * @param directorId id of the director
	 * @param tFrom timestamp not less than which observation_time_unit.begin should be
	 * @param tTo   timestamp not greater than which observation_time_unit.begin should be
	 * @return list of observations
	 */
	List<DirectorStatPoint> getDirectorStatPoints(Integer directorId, Date tFrom, Date tTo);
	
	/**
	 * Get statistic observations for a specific storage group in specified time interval.
	 * @param storageGroupId id of the director
	 * @param tFrom timestamp not less than which observation_time_unit.begin should be
	 * @param tTo   timestamp not less than which observation_time_unit.begin should be
	 * @return list of observations for the specified storage group
	 */
	List<StorageGroupStatPoint> getStorageGroupStatPoints(Integer storageGroupId, Date tFrom, Date tTo);
	

	/**
	 * Compute links between bad "problematic" states of director and "problematic" states of storage groups.
	 * @param directorId id of the director
	 * @return problemLink objects, representing potential relationship between bad states of the director and stroage groups
	 */
	List<ProblemLink> getDirectorAndSGProblemLinks(Integer directorId);
	

	
	/**
	 * Get date of the earliest observation entry for director 
	 * @return minimum date for director and storage group tables
	 */
	Date getMinDateForDirectorsAndSGStats();
	
	/**
	 * Get date of the latest observation entry for director 
	 * @return maximum date for director and storage group tables
	 */
	Date getMaxDateForDirectorsAndSGStats();
	
}
