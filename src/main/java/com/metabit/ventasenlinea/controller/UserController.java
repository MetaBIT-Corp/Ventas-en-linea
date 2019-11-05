package com.metabit.ventasenlinea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.metabit.ventasenlinea.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/usuario")
public class UserController {

	@Autowired
	@Qualifier("userServiceImpl")
	private UserServiceImpl userServiceImpl;
	
	@GetMapping("/listar")
	public ModelAndView listUsuarios() {
		ModelAndView mav = new ModelAndView("/user/listUser");
		
		mav.addObject("usersClient", userServiceImpl.findAllClientes());
		return mav;
	}
}
