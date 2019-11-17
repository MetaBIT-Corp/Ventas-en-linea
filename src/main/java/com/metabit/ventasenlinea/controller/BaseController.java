package com.metabit.ventasenlinea.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author joel
 *
 */
@Controller()
@RequestMapping({"/ventas-online","/"})
public class BaseController {
		
	/**
	 * @author joel
	 * Función que despliega la view del login 
	 */
	@GetMapping("/login")
	public ModelAndView showLoginForm(
			@RequestParam(name="error", required = false) String error,
			@RequestParam(name="logout", required = false) String logout) {
		ModelAndView modelAndView = new ModelAndView("cliente/login");
		modelAndView.addObject("error", error);
		modelAndView.addObject("logout", logout);
		return modelAndView;
	}
	
	/**
	 * @author joel
	 * Funcion que chequea el login, previamente en el file de configuration de security se 
	 * ha definido que esta url hará el check del login  
	 */
	@GetMapping({"/loginsuccess","/"})
	public String loginCheck() {
	return "redirect:/producto/index";
		
	}
	
	/**
	 * @author joel
	 * Función que despliega la view del base 
	 */
	@GetMapping("/base")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("base");
		//user de spring: import org.springframework.security.core.userdetails.User;
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		modelAndView.addObject("user", user.getUsername());
		modelAndView.addObject("role", user.getAuthorities().toArray()[0].toString());
		return modelAndView;
	}

}
