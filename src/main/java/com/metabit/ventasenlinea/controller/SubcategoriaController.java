package com.metabit.ventasenlinea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.metabit.ventasenlinea.service.SubcategoriaService;

@Controller
@RequestMapping("/departamento/categoria/{id_categoria}/subcategoria")
public class SubcategoriaController {
	//importamos servicios
	@Autowired
	@Qualifier("subcategoriaServiceImpl")
	private SubcategoriaService subcategoriaService; 
	
	
	//Listado de subcategorias
	private static final String INDEX_VIEW = "subcategoria/index";	
	
	@GetMapping("/listar")
	public ModelAndView index(@PathVariable("id_categoria") int idCategoria){
		ModelAndView mav = new ModelAndView(INDEX_VIEW);
		mav.addObject("subcategorias", subcategoriaService.listAllSubcategorias());
		return mav;
	}

}
