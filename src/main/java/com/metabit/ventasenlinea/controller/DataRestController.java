package com.metabit.ventasenlinea.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metabit.ventasenlinea.entity.ProductoCarrito;

@RestController
@RequestMapping("/api")
public class DataRestController {
	
	@GetMapping("/productos-agregados")
	public List<ProductoCarrito> getCarrito(HttpServletRequest request){
		HttpSession session = request.getSession();
		List<ProductoCarrito> productosCarritos = (ArrayList<ProductoCarrito>)session.getAttribute("productosCarrito");
		
		return productosCarritos;
	}
}
