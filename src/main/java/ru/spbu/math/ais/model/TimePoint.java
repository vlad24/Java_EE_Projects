package ru.spbu.math.ais.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="dwd_time_n_utc")
public class TimePoint {
	
	@Id
	@Column(name="timekey")
	long id;
	
	@Column(name="datestamp")
	Date timestamp;
	
	@OneToMany(mappedBy="timePoint")
	Set<DirectorStatPoint> directorStatPoints;
	
	@OneToMany(mappedBy="timePoint")
	Set<StorageGroupStatPoint> storageGroupStatPoints;
	
 	public Set<DirectorStatPoint> getDirectorStatPoints() {
		return directorStatPoints;
	}

	public void setDirectorStatPoints(Set<DirectorStatPoint> directorStatPoints) {
		this.directorStatPoints = directorStatPoints;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
		return "TimePoint [id=" + id + ", timestamp=" + timestamp + "]";
	}
	
}
