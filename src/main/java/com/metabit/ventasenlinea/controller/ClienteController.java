package com.metabit.ventasenlinea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.metabit.ventasenlinea.entity.Cliente;
import com.metabit.ventasenlinea.entity.User;
import com.metabit.ventasenlinea.service.impl.ClienteServiceImpl;
import com.metabit.ventasenlinea.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	@Qualifier("clienteServiceImpl")
	private ClienteServiceImpl clienteServiceImpl;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserServiceImpl userServiceImpl;
	
	@GetMapping("/crear-cliente")
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView("cliente/crearCliente");
		
		mav.addObject("user", new User());
		mav.addObject("cliente", new Cliente());
		
		return mav;
	}
	
	@PostMapping("/crear-cliente")
	public String store(@ModelAttribute("cliente") Cliente cliente, @ModelAttribute("user") User user, RedirectAttributes redirAttrs) {
		String codigo = codigo();
		
		if(!user.getPassword().equals(user.getPasswordConfirm())) {
			redirAttrs.addFlashAttribute("message", "Las contraseñas no coinciden");
			return "redirect:/cliente/crear-cliente";
		}
		user.setCodigoVerificacion(codigo);
		user.setRole(3);
		user.setVerifyed(0);
		//user.setPassword();
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
		user.setPassword(pe.encode(user.getPassword())); 
		
		cliente.setUser(user);
		
		userServiceImpl.createUser(user);
		clienteServiceImpl.createCliente(cliente);
		
		return "redirect:/cliente/verificar-codigo/"+user.getIdUser();
	}
	
	@GetMapping("/verificar-codigo/{id}")
	public ModelAndView verficarCodigoGET(@PathVariable("id") int id) {
		ModelAndView mav = new ModelAndView("cliente/verificarCodigo");
		User user = userServiceImpl.findById(id);
		mav.addObject("user", user);
		
		return mav;
	}
	
	@PostMapping("/verificar-codigo")
	public String verificarCodigoPost(@RequestParam("codigo") String codigo, @RequestParam("id_user") int id_user, RedirectAttributes redirAttrs) {
		User user = userServiceImpl.findById(id_user);
		if(user.getCodigoVerificacion().equals(codigo)) {
			user.setVerifyed(1);
			userServiceImpl.createUser(user);
			redirAttrs.addFlashAttribute("message", "Guud");
			return "redirect:/ventas-online/login";
		}else {
			redirAttrs.addFlashAttribute("message", "El código ingresado es incorrecto");
			return "redirect:/cliente/verificar-codigo/"+id_user;
		}
	}
	
	public String codigo() {
		String codigo = "";
		char[] numeros = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		
		for (int i = 0; i < 6; i++) {
			codigo += numeros[(int)(Math.random() * (10))];
		}
		
		return codigo;
	}
	
	

}
