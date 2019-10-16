package com.metabit.ventasenlinea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.metabit.ventasenlinea.entity.Producto;
import com.metabit.ventasenlinea.service.ProductoService;

@Controller
@RequestMapping("/producto")
public class ProductoController {
	@Autowired
	@Qualifier("productoServiceImpl")
	private ProductoService productService;

	@GetMapping("/index")
	public ModelAndView indexProducto() {
		ModelAndView mav = new ModelAndView("/producto/index");

		// Borramos toda la BD para evitar que se repitan
		productService.deleteAll();

		// Agregando productos a la BD
		Producto p1 = new Producto();
		p1.setImagen("/img_products/nintendo.jpg");
		p1.setMarca("Nintento");
		p1.setTitulo("Nintendo Switch");
		p1.setDescripcionArticulo("Consola de videojuego que puede jugarse en modo portatil y modo Dock");
		productService.addProduct(p1);

		Producto p2 = new Producto();
		p2.setImagen("/img_products/play.jpg");
		p2.setMarca("Sony");
		p2.setTitulo("Play Station 5");
		p2.setDescripcionArticulo("Consola de video juego con potencia para correr juegos en 4K");
		productService.addProduct(p2);

		Producto p3 = new Producto();
		p3.setImagen("/img_products/xbox.jpg");
		p3.setMarca("Microsoft");
		p3.setTitulo("Xbox One S");
		p3.setDescripcionArticulo("Consola con potencia para 4K, microprocesador Scorpio con 100teraflops");
		productService.addProduct(p3);

		/*
		Producto p4 = new Producto();
		p4.setImagen("/img_products/nintendo.jpg");
		p4.setMarca("Nintento");
		p4.setTitulo("Nintendo Switch");
		p4.setDescripcionArticulo("Consola de videojuego que puede jugarse en modo portatil y modo Dock");
		productService.addProduct(p4);

		Producto p5 = new Producto();
		p5.setImagen("/img_products/play.jpg");
		p5.setMarca("Sony");
		p5.setTitulo("Play Station 5");
		p5.setDescripcionArticulo("Consola de video juego con potencia para correr juegos en 4K");
		productService.addProduct(p5);

		Producto p6 = new Producto();
		p6.setImagen("/img_products/xbox.jpg");
		p6.setMarca("Microsoft");
		p6.setTitulo("Xbox One S");
		p6.setDescripcionArticulo("Consola con potencia para 4K, microprocesador Scorpio con 100teraflops");
		productService.addProduct(p6);*/

		// Recuperamos todos los datos de la BD
		mav.addObject("productos", productService.getProductos());

		return mav;
	}
}
