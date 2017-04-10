package ru.spbu.math.ais.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.spbu.math.ais.dao.DirectorDAO;
import ru.spbu.math.ais.dao.StatsDAO;
import ru.spbu.math.ais.model.Director;
import ru.spbu.math.ais.model.StorageGroup;
import ru.spbu.math.ais.wrappers.ProblemLink;
import ru.spbu.math.ais.wrappers.ProblemReport;

@Service
public class ProblemsService {

	@Autowired
	private DirectorDAO directorDAO;
	@Autowired
	private StatsDAO statsDAO;
	
	
	/**
	 * Gets problematic directors from dataSource and converts it to uniformly structured problem-objects
	 * A problematic director is defined by user and impacts DAO layer.
	 * @return list of problematic directors
	 */
	@Transactional
	public List<ProblemReport> getProblematicDirectors() {
		List<Director> directors = statsDAO.getProlematicDirectors();
		List<ProblemReport> reports = new ArrayList<ProblemReport>();
		for (Director director : directors){
			reports.add(
					new ProblemReport.Builder()
						.setCrux("Director's queue was detected to be too long")
						.setPlace(director.getName())
						.setMeta(String.valueOf(director.getId()))
						.build()
			);
		}
		return reports;
	}
	
	/**
	 * Gets problematic storage groups from dataSource and converts it to uniformly structured problem-objects
	 * A "problematic storage group term" is defined by user and impacts DAO layer.
	 * @return list of problematic directors
	 */
	@Transactional
	public List<ProblemReport> getProblematicStorageGroups() {
		List<StorageGroup> storageGroups = statsDAO.getProblematicStorageGroups();
		List<ProblemReport> reports = new ArrayList<ProblemReport>();
		for (StorageGroup storageGroup : storageGroups){
			reports.add(
					new ProblemReport.Builder()
						.setCrux("Long response time")
						.setPlace(storageGroup.getName())
						.setMeta(String.valueOf(storageGroup.getId()))
						.build()
			);
		}
		return reports;
	}
	
	/**
	 * Establishes potential reason-consequences relationships between bad states of a director and storage groups and claim potential problems. 
	 * Data is returned in web pretty-print form.
	 * @param directorId director identifier 
	 * @return list of potential problems 
	 */
	@Transactional
	public List<ProblemReport> analyzeProblemsOfDirector(int directorId) {
		List<ProblemLink> problemLinks = statsDAO.getDirectorAndSGProblemLinks(directorId);
		List<ProblemReport> reports = new ArrayList<ProblemReport>(problemLinks.size());
		for (ProblemLink link : problemLinks) {
			ProblemReport report = new ProblemReport.Builder()
						.setPlace(link.getDirectorStatPoint().getDirector().getName())
						.setTime(link.getTimePoint().getTimestamp().toString())
						.setCrux("Queue overlow").appendToCrux("<br>")
						.appendToCrux(" bucket7Load=" + link.getDirectorStatPoint().getBucket7Load().intValue()).appendToCrux("<br>")
						.appendToCrux(" bucket8Load=" + link.getDirectorStatPoint().getBucket8Load().intValue()).appendToCrux("<br>")
						.appendToCrux(" bucket9Load=" + link.getDirectorStatPoint().getBucket9Load().intValue()).appendToCrux("<br>")
						.setReason(link.getStorageGroupStatPoint().getStorageGroup().getName()).appendToReason("<br>")
						.appendToReason(" bucket5writeRt=" + link.getStorageGroupStatPoint().getBucket5WriteRt().intValue()).appendToReason("; ")
						.appendToReason(" bucket5readRt="  + link.getStorageGroupStatPoint().getBucket5ReadRt().intValue()).appendToReason("<br>")
						.appendToReason(" bucket6writeRt=" + link.getStorageGroupStatPoint().getBucket6WriteRt().intValue()).appendToReason("; ")
						.appendToReason(" bucket6readRt="  + link.getStorageGroupStatPoint().getBucket6ReadRt().intValue()).appendToReason("<br>")
						.appendToReason(" bucket7writeRt=" + link.getStorageGroupStatPoint().getBucket7WriteRt().intValue()).appendToReason("; ")
						.appendToReason(" bucket7readRt="  + link.getStorageGroupStatPoint().getBucket7ReadRt().intValue()).appendToReason("<br>")
						.build();
			reports.add(report);
		}
		return reports;
	}


}
