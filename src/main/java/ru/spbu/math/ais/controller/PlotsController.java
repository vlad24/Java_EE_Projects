package ru.spbu.math.ais.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.spbu.math.ais.model.Director;
import ru.spbu.math.ais.model.StorageGroup;
import ru.spbu.math.ais.service.PlotService;
import ru.spbu.math.ais.wrappers.PlotRequest;

/**
 * @author vlad
 * Controller responsible for serving requests for plotting
 */
@Controller
public class PlotsController {

	private static final SimpleDateFormat FORM_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	@Autowired
	PlotService plotService;
	
	@RequestMapping(value = "/plots", method = RequestMethod.GET)
	public String getPlotRequestForm(Model model) {
		List<Director> availableDirectors = plotService.getAvailableDirectors();
		List<StorageGroup> availableStorageGroups = plotService.getAvailableStorageGroups();
		PlotRequest initialRequest = new PlotRequest();
		initialRequest.setDirectorId(availableDirectors.get(0).getId());
		initialRequest.setSgId(availableStorageGroups.get(0).getId());
		model.addAttribute("plotRequest", initialRequest);
		model.addAttribute("maxDate", FORM_DATE_FORMAT.format(plotService.getMaxInputDate()));
		model.addAttribute("minDate", FORM_DATE_FORMAT.format(plotService.getMinInputDate()));
		model.addAttribute("directors", availableDirectors);
		model.addAttribute("storageGroups",	availableStorageGroups);
		return "plots";
	}
	
	
	@RequestMapping(value = "/showPlot", method = RequestMethod.GET)
	public String getPlotsData(
			@ModelAttribute("plotRequest") PlotRequest plotRequest, ModelMap model) throws ParseException {
		model.addAttribute("maxDate", FORM_DATE_FORMAT.format(plotService.getMaxInputDate()));
		model.addAttribute("minDate", FORM_DATE_FORMAT.format(plotService.getMinInputDate()));
		model.addAttribute("directors", plotService.getAvailableDirectors());
		model.addAttribute("storageGroups",	plotService.getAvailableStorageGroups());
		model.addAttribute("plotRequest", plotRequest);
		//Could validate dates here, but better at client
		model.addAttribute("directorStatPoints", plotService.getDirectorStatPointsSimplyJsoned(plotRequest.getDirectorId(),
												FORM_DATE_FORMAT.parse(plotRequest.getTFromDir()), 
												FORM_DATE_FORMAT.parse(plotRequest.getTToDir())));
		model.addAttribute("sgStatPoints", plotService.getSFStatPointsSimplyJsoned(plotRequest.getSgId(),
												FORM_DATE_FORMAT.parse(plotRequest.getTFromSG()),
												FORM_DATE_FORMAT.parse(plotRequest.getTToSG())));
		return "plots";
	}
	
	

}
