package ru.spbu.math.ais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ru.spbu.math.ais.service.ProblemsService;
import ru.spbu.math.ais.wrappers.ProblemReport;


/**
 * @author vlad
 * Controller addressed when user needs to examine system's problems
 */
@Controller
public class ProblemDetectController {

	@Autowired
	ProblemsService problemsService;
	
	
	@RequestMapping(value = "/problems", method = RequestMethod.GET)
	public String getProblems(ModelMap model) {
		return "problems";
	}
	
	@RequestMapping(value = "/problems/directors", method = RequestMethod.GET)
	public String getProblemDirectors(ModelMap model) {
		List<ProblemReport> problematicDirectors = problemsService.getProblematicDirectors();
		model.addAttribute("problems", problematicDirectors);
		return "problemList";
	}
	
	
	@RequestMapping(value = "/problems/directors", method = RequestMethod.GET, params="id")
	public String analyzeProblemDirector(@RequestParam("id") int directorId, ModelMap model) {
		List<ProblemReport> reports = problemsService.analyzeProblemsOfDirector(directorId);
		model.addAttribute("problems", reports);
		return "problemList";
	}
	
	
	@RequestMapping(value = "/problems/storageGroups", method = RequestMethod.GET)
	public String getProblemGroups(ModelMap model) {
		List<ProblemReport> problematicGroups = problemsService.getProblematicStorageGroups();
		model.addAttribute("problems", problematicGroups);
		return "problemList";
	}
	
	@RequestMapping(value = "/problems/storageGroups", method = RequestMethod.GET, params="id")
	public String analyzeProblemStorageGroup(@RequestParam("id") int sgId, ModelMap model) {
		return "unavailableYet";
	}
	
}
