package ru.spbu.math.ais.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;


@Entity
@Table(name="dwf_fedirector_r")
public class DirectorStatPoint {
	
	@Override
	public String toString() {
		return "DirectorStatPoint [timePoint=" + timePoint + ", director="
				+ director + ", bucket7Load=" + bucket7Load + ", bucket8Load="
				+ bucket8Load + ", bucket9Load=" + bucket9Load + "]";
	}

	@EmbeddedId
	PKDirectorStatPoint primaryKey;
	
	@ManyToOne
	@MapsId("timePointId")
	@JoinColumn(name = "timekey", referencedColumnName="timekey")
	private TimePoint timePoint;
	
	@ManyToOne
	@MapsId("directorId")
	@JoinColumn(name = "fedirectorkey")
	private Director director;
	
	
	@Column(name="spmqueuedepcount7")
	Double bucket7Load;
	@Column(name="spmqueuedepcount8")
	Double bucket8Load;
	@Column(name="spmqueuedepcount9")
	Double bucket9Load;
	
	
	
//	@Column(name="spmaccumqueuedep1")
//	Double bucket1Load;
//	@Column(name="spmaccumqueuedep2")
//	Double bucket2Load;
//	@Column(name="spmaccumqueuedep3")
//	Double bucket3Load;
//	@Column(name="spmaccumqueuedep4")
//	Double bucket4Load;
//	@Column(name="spmaccumqueuedep5")
//	Double bucket5Load;

	
	public TimePoint getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(TimePoint timePoint) {
		this.timePoint = timePoint;
	}
	public Director getDirector() {
		return director;
	}
	public void setDirector(Director director) {
		this.director = director;
	}
	public Double getBucket7Load() {
		return bucket7Load;
	}
	public void setBucket7Load(Double bucket7Load) {
		this.bucket7Load = bucket7Load;
	}
	public Double getBucket8Load() {
		return bucket8Load;
	}
	public void setBucket8Load(Double bucket8Load) {
		this.bucket8Load = bucket8Load;
	}
	public Double getBucket9Load() {
		return bucket9Load;
	}
	public void setBucket9Load(Double bucket9Load) {
		this.bucket9Load = bucket9Load;
	}
//	public Set<StorageGroupStatPoint> getSgStatPoints() {
//		return sgStatPoints;
//	}
//	public void setSgStatPoints(Set<StorageGroupStatPoint> sgStatPoints) {
//		this.sgStatPoints = sgStatPoints;
//	}
		
}
