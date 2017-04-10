package ru.spbu.math.ais.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PKStorageGroupStatPoint implements Serializable{
	
	long timePointId;
	int storageGroupId;
	
	public long getTimePointId() {
		return timePointId;
	}
	public void setTimePointId(long timePointId) {
		this.timePointId = timePointId;
	}
	public int getStorageGroupId() {
		return storageGroupId;
	}
	public void setStorageGroupId(int storageGroupId) {
		this.storageGroupId = storageGroupId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + storageGroupId;
		result = prime * result + (int) (timePointId ^ (timePointId >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PKStorageGroupStatPoint other = (PKStorageGroupStatPoint) obj;
		if (storageGroupId != other.storageGroupId)
			return false;
		if (timePointId != other.timePointId)
			return false;
		return true;
	}
	
}
