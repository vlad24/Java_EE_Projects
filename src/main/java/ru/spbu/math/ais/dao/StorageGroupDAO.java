package ru.spbu.math.ais.dao;

import java.util.List;

import ru.spbu.math.ais.model.StorageGroup;

public interface StorageGroupDAO {

	/**
	 * Get all groups
	 * @return list of all groups present
	 */
	List<StorageGroup> getAllGroups();
	

}
