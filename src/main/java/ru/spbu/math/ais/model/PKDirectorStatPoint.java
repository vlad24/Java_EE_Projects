package ru.spbu.math.ais.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PKDirectorStatPoint implements Serializable{
	
	//@Column(name = "timekey")
	long timePointId;
	//@Column(name = "fedirectorkey")
	int directorId;
	
	
	public int getDirId() {
		return directorId;
	}
	public void setDirId(int dirId) {
		this.directorId = dirId;
	}
	public long gettId() {
		return timePointId;
	}
	public void settId(long tId) {
		this.timePointId = tId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PKDirectorStatPoint other = (PKDirectorStatPoint) obj;
		if (directorId != other.directorId)
			return false;
		if (timePointId != other.timePointId)
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + directorId;
		result = prime * result + (int) (timePointId ^ (timePointId >>> 32));
		return result;
	}
	
}
