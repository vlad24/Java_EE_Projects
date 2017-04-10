package ru.spbu.math.ais.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author vlad
 * Simple intercept-all controller. 
 * Intended to get the program flow when an exception occurs
 */
@ControllerAdvice
public class GlobalExceptionController {


	@ExceptionHandler(Exception.class)
	public ModelAndView handleExceptions(Exception ex) {
		ModelAndView model = new ModelAndView("error");
		model.addObject("exceptionReason", ex.getMessage());
		return model;

	}

}