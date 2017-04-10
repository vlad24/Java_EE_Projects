package ru.spbu.math.ais.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;


@Entity
@Table(name="dwf_storagegroup_r")
public class StorageGroupStatPoint {
	
	@EmbeddedId
	PKStorageGroupStatPoint primaryKey;
	
	@ManyToOne
	@MapsId("timePointId")
	@JoinColumn(name = "timekey")
	private TimePoint timePoint;
	
	@ManyToOne
	@MapsId("storageGroupId")
	@JoinColumn(name = "storagegroupkey")
	private StorageGroup storageGroup;

	@Column(name="spmreadrtcount5")
	Double bucket5ReadRt;
	@Column(name="spmreadrtcount6")
	Double bucket6ReadRt;
	@Column(name="spmreadrtcount7")
	Double bucket7ReadRt;
	
	@Column(name="spmwritertcount5")
	Double bucket5WriteRt;
	@Column(name="spmwritertcount6")
	Double bucket6WriteRt;
	@Column(name="spmwritertcount7")
	Double bucket7WriteRt;

	public TimePoint getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(TimePoint timePoint) {
		this.timePoint = timePoint;
	}
	public StorageGroup getStorageGroup() {
		return storageGroup;
	}
	public void setStorageGroup(StorageGroup storageGroup) {
		this.storageGroup = storageGroup;
	}
	public Double getBucket5ReadRt() {
		return bucket5ReadRt;
	}
	public void setBucket5ReadRt(Double bucket5ReadRt) {
		this.bucket5ReadRt = bucket5ReadRt;
	}
	public Double getBucket6ReadRt() {
		return bucket6ReadRt;
	}
	public void setBucket6ReadRt(Double bucket6ReadRt) {
		this.bucket6ReadRt = bucket6ReadRt;
	}
	public Double getBucket7ReadRt() {
		return bucket7ReadRt;
	}
	public void setBucket7ReadRt(Double bucket7ReadRt) {
		this.bucket7ReadRt = bucket7ReadRt;
	}
	public Double getBucket5WriteRt() {
		return bucket5WriteRt;
	}
	public void setBucket5WriteRt(Double bucket5WriteRt) {
		this.bucket5WriteRt = bucket5WriteRt;
	}
	public Double getBucket6WriteRt() {
		return bucket6WriteRt;
	}
	public void setBucket6WriteRt(Double bucket6WriteRt) {
		this.bucket6WriteRt = bucket6WriteRt;
	}
	public Double getBucket7WriteRt() {
		return bucket7WriteRt;
	}
	public void setBucket7WriteRt(Double bucket7WriteRt) {
		this.bucket7WriteRt = bucket7WriteRt;
	}
	
	@Override
	public String toString() {
		return "StorageGroupStatPoint [timePoint=" + timePoint
				+ ", storageGroup=" + storageGroup + ", bucket5ReadRt="
				+ bucket5ReadRt + ", bucket6ReadRt=" + bucket6ReadRt
				+ ", bucket7ReadRt=" + bucket7ReadRt + ", bucket5WriteRt="
				+ bucket5WriteRt + ", bucket6WriteRt=" + bucket6WriteRt
				+ ", bucket7WriteRt=" + bucket7WriteRt + "]";
	}
		
}
