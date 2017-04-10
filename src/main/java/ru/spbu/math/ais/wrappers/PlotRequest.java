package ru.spbu.math.ais.wrappers;



/**
 * @author vlad
 *
 * Class representing a user request for plotting entities' statistics.
 * It ids of of a director and storage group + time interval for each entity.
 * Result observation units for each entity will have start time in which corresponding time interval.
 * 
 */
public class PlotRequest {
	int directorId;
    int sgId;
	String tFromDir;
	String tToDir;
	String tFromSG;
	String tToSG;
	public int getDirectorId() {
		return directorId;
	}
	public void setDirectorId(int directorId) {
		this.directorId = directorId;
	}
	public int getSgId() {
		return sgId;
	}
	public void setSgId(int sgId) {
		this.sgId = sgId;
	}
	public String getTFromDir() {
		return tFromDir;
	}
	public void settFromDir(String tFromDir) {
		this.tFromDir = tFromDir;
	}
	public String getTToDir() {
		return tToDir;
	}
	public void settToDir(String tToDir) {
		this.tToDir = tToDir;
	}
	public String getTFromSG() {
		return tFromSG;
	}
	public void settFromSG(String tFromSG) {
		this.tFromSG = tFromSG;
	}
	public String getTToSG() {
		return tToSG;
	}
	public void settToSG(String tToSG) {
		this.tToSG = tToSG;
	}
	@Override
	public String toString() {
		return "PlotRequest [directorId=" + directorId + ", sgId=" + sgId
				+ ", tFromDir=" + tFromDir + ", tToDir=" + tToDir
				+ ", tFromSG=" + tFromSG + ", tToSG=" + tToSG + "]";
	}
	
}
