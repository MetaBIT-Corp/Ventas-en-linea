package com.metabit.ventasenlinea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller()
@RequestMapping("/ventas-online")
public class BaseController {
	
	@GetMapping("")
	public ModelAndView base() {
		ModelAndView modelAndView = new ModelAndView("base");
		return modelAndView;
	}
	
	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("index");
		return modelAndView;
	}

}
