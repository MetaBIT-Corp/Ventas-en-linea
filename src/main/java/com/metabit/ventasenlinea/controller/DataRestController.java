package com.metabit.ventasenlinea.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.metabit.ventasenlinea.entity.Producto;
import com.metabit.ventasenlinea.entity.ProductoCarrito;
import com.metabit.ventasenlinea.entity.User;
import com.metabit.ventasenlinea.service.ProductoService;
import com.metabit.ventasenlinea.service.UserService;
import com.metabit.ventasenlinea.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api")
public class DataRestController {
	ArrayList<ProductoCarrito> productosCarrito = new ArrayList<ProductoCarrito>();
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserServiceImpl userServiceImpl;
	
	@GetMapping("/productos-agregados")
	public List<ProductoCarrito> getCarrito(HttpServletRequest request){
		HttpSession session = request.getSession();
		List<ProductoCarrito> productosCarritos = (ArrayList<ProductoCarrito>)session.getAttribute("productosCarrito");
		
		return productosCarritos;
	}
	
	/*@GetMapping("/user-email/{email}")
	public String getUserEmail(@PathVariable("email") String email){
		System.out.println("--------------------------------------"+email);
		User user = new User();
		user = userServiceImpl.findByEmail(email);
		
		System.out.println("--------------------------------------"+user.getEmail());
		return user.getEmail();
	}*/
}
