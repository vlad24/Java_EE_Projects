package ru.spbu.math.ais.dao;

import java.util.List;
import ru.spbu.math.ais.model.Director;

public interface DirectorDAO {
	/**
	 * Get all directors accounted by system.
	 * @return list of all directors
	 */
	public List<Director> getAllDirectors();
}
