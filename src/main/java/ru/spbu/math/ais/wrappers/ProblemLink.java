package ru.spbu.math.ais.wrappers;

import ru.spbu.math.ais.model.DirectorStatPoint;
import ru.spbu.math.ais.model.StorageGroupStatPoint;
import ru.spbu.math.ais.model.TimePoint;

/**
 * @author vlad
 *
 * Wrapper class that represents potential relationship between bad state of a director and state of a storage group
 */
public class ProblemLink {
	private TimePoint             timePoint;
	private DirectorStatPoint     directorStatPoint;
	private StorageGroupStatPoint storageGroupStatPoint;
	
	public ProblemLink(TimePoint timePoint,
			DirectorStatPoint directorStatPoint,
			StorageGroupStatPoint storageGroupStatPoint) {
		super();
		assert (directorStatPoint.getTimePoint().getId()    == timePoint.getId() &&
				storageGroupStatPoint.getTimePoint().getId() == timePoint.getId());	
		this.timePoint = timePoint;
		this.directorStatPoint = directorStatPoint;
		this.storageGroupStatPoint = storageGroupStatPoint;
	}
	
	public TimePoint getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(TimePoint timePoint) {
		this.timePoint = timePoint;
	}
	public DirectorStatPoint getDirectorStatPoint() {
		return directorStatPoint;
	}
	public void setDirectorStatPoint(DirectorStatPoint directorStatPoint) {
		this.directorStatPoint = directorStatPoint;
	}
	public StorageGroupStatPoint getStorageGroupStatPoint() {
		return storageGroupStatPoint;
	}
	public void setStorageGroupStatPoint(StorageGroupStatPoint storageGroupStatPoint) {
		this.storageGroupStatPoint = storageGroupStatPoint;
	}
	
}
