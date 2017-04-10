package ru.spbu.math.ais.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.spbu.math.ais.dao.DirectorDAO;
import ru.spbu.math.ais.dao.StatsDAO;
import ru.spbu.math.ais.dao.StorageGroupDAO;
import ru.spbu.math.ais.model.Director;
import ru.spbu.math.ais.model.DirectorStatPoint;
import ru.spbu.math.ais.model.PKDirectorStatPoint;
import ru.spbu.math.ais.model.PKStorageGroupStatPoint;
import ru.spbu.math.ais.model.StorageGroup;
import ru.spbu.math.ais.model.StorageGroupStatPoint;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class PlotService {

	private Gson minimalisticJsoner;

	@Autowired
	DirectorDAO directorDAO;

	@Autowired
	StorageGroupDAO storageGroupsDAO;

	@Autowired
	StatsDAO statsDAO;

	/**
	 * Return the maximum user input date for queries that makes sense
	 * @return max date present in database for director/storageGroup statistic
	 */
	@Transactional
	public Date getMaxInputDate(){
		return statsDAO.getMaxDateForDirectorsAndSGStats();
	}

	/**
	 * Return the minimum user input date for queries that makes sense
	 * @return minimum date
	 */
	@Transactional
	public Date getMinInputDate(){
		return statsDAO.getMinDateForDirectorsAndSGStats();
	}

	/**
	 * Get all directors present in database at the time of invocation
	 * @return list of directors
	 */
	@Transactional
	public List<Director> getAvailableDirectors(){
		return directorDAO.getAllDirectors();
	}

	/**
	 * Get all storage groups present in database at the time of invocation
	 * @return list of groups
	 */
	@Transactional
	public List<StorageGroup> getAvailableStorageGroups(){
		return storageGroupsDAO.getAllGroups();
	}

	@Transactional
	public List<DirectorStatPoint> getDirectorStatPoints(int id, Date tFrom, Date tTo) {
		return statsDAO.getDirectorStatPoints(id, tFrom, tTo);
	}


	@Transactional
	public List<StorageGroupStatPoint> getSFStatPoints(int sgId, Date tFrom, Date tTo) {
		return statsDAO.getStorageGroupStatPoints(sgId, tFrom, tTo);
	}

	/**
	 * Returns json representation of array of storage group stat points 
	 * @param sgId storageGroup Id
	 * @param tFrom timestamp not less than which observation_time_unit.begin should be
	 * @param tTo   timestamp not greater than which observation_time_unit.begin should be
	 * @return json strng
	 */
	@Transactional
	public String getSFStatPointsSimplyJsoned(int sgId, Date tFrom, Date tTo) {
		return minimalisticJsoner.toJson(statsDAO.getStorageGroupStatPoints(sgId, tFrom, tTo));
	}

	/**
	 * Returns json representation of array of director stat points 
	 * @param id director id
	 * @param tFrom timestamp not less than which observation_time_unit.begin should be
	 * @param tTo   timestamp not greater than which observation__time_unit.begin should be
	 * @return json string
	 */
	@Transactional
	public String getDirectorStatPointsSimplyJsoned(int id, Date tFrom, Date tTo) {
		return minimalisticJsoner.toJson(statsDAO.getDirectorStatPoints(id, tFrom, tTo));
	}

	@PostConstruct
	public void init(){
		minimalisticJsoner = new GsonBuilder()
		.setExclusionStrategies(new ExclusionStrategy() {

			public boolean shouldSkipClass(Class<?> clazz) {
				return (clazz == Set.class);
			}

			public boolean shouldSkipField(FieldAttributes f) {
				return 
						(f.getName() == "id") ||
						(f.getDeclaredClass() == StorageGroup.class) ||
						(f.getDeclaredClass() == Director.class)  ||
						(f.getDeclaredClass() == PKStorageGroupStatPoint.class) ||
						(f.getDeclaredClass() == PKDirectorStatPoint.class);
			}
		})
		.create();
	}


}
