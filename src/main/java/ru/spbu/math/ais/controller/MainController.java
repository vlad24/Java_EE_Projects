package ru.spbu.math.ais.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author vlad
 * Main controller that serves general user queries
 */
@Controller
public class MainController {

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String getMainPage() {
		return "main";
	}
	
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String getAboutPage() {
		return "about";
	}

	
}
