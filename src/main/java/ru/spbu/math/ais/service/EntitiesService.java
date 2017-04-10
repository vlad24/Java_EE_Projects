package ru.spbu.math.ais.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.spbu.math.ais.dao.DirectorDAO;
import ru.spbu.math.ais.dao.StorageGroupDAO;
import ru.spbu.math.ais.model.Director;
import ru.spbu.math.ais.model.StorageGroup;

@Service
public class EntitiesService {

	@Autowired
	private DirectorDAO directorDAO;
	
	@Autowired
	private StorageGroupDAO storageGroupDAO;
	
	/**
	 * Get list of all directors present in system.
	 * Directly addresses DAO layer.
	 * @return list of directors
	 */
	public List<Director> getAllDirectors() {
		return directorDAO.getAllDirectors();
	}
	
	/**
	 * Get list of all storage groups present in system.
	 * Directly addresses DAO layer.
	 * @return list of storage groups
	 */
	public List<StorageGroup> getAllStorageGroups() {
		return storageGroupDAO.getAllGroups();
	}

}
