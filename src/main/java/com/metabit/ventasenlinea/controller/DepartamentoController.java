package com.metabit.ventasenlinea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.metabit.ventasenlinea.service.DepartamentoService;

@Controller
@RequestMapping("/departamento")
public class DepartamentoController {

	@Autowired
	@Qualifier("departamentoServiceImpl")
	private DepartamentoService departamentoService;
	
	private static final String INDEX_VIEW = "departamento/index";
	
	@GetMapping("/listar")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView(INDEX_VIEW);
		mav.addObject("departamentos", departamentoService.getDepartamentos());
		//user
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mav.addObject("user", user.getUsername());
		mav.addObject("role", user.getAuthorities().toArray()[0]);
		return mav;
	}
}
