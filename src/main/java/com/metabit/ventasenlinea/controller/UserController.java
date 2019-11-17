package com.metabit.ventasenlinea.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.metabit.ventasenlinea.entity.Cliente;
import com.metabit.ventasenlinea.entity.Producto;
import com.metabit.ventasenlinea.entity.User;
import com.metabit.ventasenlinea.service.EmpleadoService;
import com.metabit.ventasenlinea.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/usuario")
public class UserController {

	@Autowired
	@Qualifier("userServiceImpl")
	private UserServiceImpl userServiceImpl;
	@Autowired
	@Qualifier("empleadoServiceImpl")
	private EmpleadoService empleadoService;
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/listar")
	public ModelAndView listUsuarios() {
		ModelAndView mav = new ModelAndView("/user/listUser");
		mav.addObject("empleados",empleadoService.getEmpleados());
		mav.addObject("usersClient", userServiceImpl.findAllClientes());
		
		List<Cliente> clientes = userServiceImpl.findAllClientes();
		
		System.out.println("-------------------"+clientes.get(0).getUser().getUserRoles().get(0).getRole());
		return mav;
	}
	
	
	@PostMapping("/deshabilitar")
	public String DeshabUsuario(HttpServletRequest request) {
		int usuario_id = Integer.parseInt(request.getParameter("usuario_id_deshab"));
		User usuario = userServiceImpl.findById(usuario_id);
		usuario.setVerifyed(0);
		userServiceImpl.updateUser(usuario);
		
		return "redirect:/usuario/listar";
	}
	
	
}
