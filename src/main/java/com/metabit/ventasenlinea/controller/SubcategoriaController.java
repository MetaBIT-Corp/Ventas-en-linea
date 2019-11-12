package com.metabit.ventasenlinea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/subcategoria")
public class SubcategoriaController {
	
	@GetMapping("/index")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("subcategoria/index");
		return mav;
	}

}
