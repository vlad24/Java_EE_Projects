package ru.spbu.math.ais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.spbu.math.ais.model.Director;
import ru.spbu.math.ais.model.StorageGroup;
import ru.spbu.math.ais.service.EntitiesService;



/**
 * @author vlad
 * Controller that is responsible for serving user requests aimed to list entities.
 */
@Controller
public class EntityListController {

	@Autowired
	EntitiesService entitiesService;
	
	
	@RequestMapping(value = "/entities", method = RequestMethod.GET)
	public String getListPage(ModelMap model) {
		return "entities";
	}
	
	@RequestMapping(value = "/entities/directors", method = RequestMethod.GET)
	public String getDirectors(ModelMap model) {
		List<Director> directors = entitiesService.getAllDirectors();
		model.addAttribute("entities", directors);
		model.addAttribute("type", "Directors");
		return "entitiyList";
	}
	
	@RequestMapping(value = "/entities/storageGroups", method = RequestMethod.GET)
	public String getStorageGroups(ModelMap model) {
		List<StorageGroup> storageGroups = entitiesService.getAllStorageGroups();
		model.addAttribute("entities", storageGroups);
		model.addAttribute("type", "Storage Groups");
		return "entitiyList";
	}
	

}

