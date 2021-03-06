package com.metabit.ventasenlinea.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.metabit.ventasenlinea.entity.Categoria;
import com.metabit.ventasenlinea.entity.Departamento;
import com.metabit.ventasenlinea.entity.Kardex;
import com.metabit.ventasenlinea.entity.Producto;
import com.metabit.ventasenlinea.entity.ProductoCarrito;
import com.metabit.ventasenlinea.entity.Subcategoria;
import com.metabit.ventasenlinea.service.CategoriaService;
import com.metabit.ventasenlinea.service.DepartamentoService;

import com.metabit.ventasenlinea.service.KardexService;
import com.metabit.ventasenlinea.service.ProductoService;
import com.metabit.ventasenlinea.service.SubcategoriaService;

@Controller
@RequestMapping("/producto")
public class ProductoController {

	private static final String INDEX_VIEW = "/producto/index";
	ArrayList<ProductoCarrito> productosCarrito = new ArrayList<ProductoCarrito>();

	@Autowired
	@Qualifier("productoServiceImpl")
	private ProductoService productService;

	@Autowired
	@Qualifier("subcategoriaServiceImpl")
	private SubcategoriaService subcategoriaService;

	@Autowired
	@Qualifier("kardexServiceImpl")
	private KardexService kardexService;

	@Autowired
	@Qualifier("departamentoServiceImpl")
	private DepartamentoService departamentoService;
	@Autowired
	@Qualifier("categoriaServiceImpl")
	private CategoriaService categoriaService;

	@GetMapping("/index")
	public ModelAndView indexProducto(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(INDEX_VIEW);
		HttpSession session = request.getSession();
		ArrayList<ProductoCarrito> getProductos;
		getProductos = (ArrayList<ProductoCarrito>) session.getAttribute("productosCarrito");

		mav.addObject("departamentos", ValidarDepartamento());


		mav.addObject("esProducto", 1);
		mav.addObject("Home", ValidarDepartamento());
		mav.addObject("productos", ValidarProductos(productService.getProductos()));
		// Si el usuario está autenticado devuelve a la vista el username y el role
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (isUserLoggedIn()) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			mav.addObject("user", userDetail.getUsername());
			mav.addObject("role", userDetail.getAuthorities().toArray()[0].toString());
			if (userDetail.getAuthorities().toArray()[0].toString().equals("ROLE_ADMIN")) {
				mav.setViewName("redirect:/departamento/listar"); // cambiar a listado de productos (Datatable)
			}
			if (userDetail.getAuthorities().toArray()[0].toString().equals("ROLE_VENTAS")) {
				mav.setViewName("redirect:/pedido/list");
			}
			if (userDetail.getAuthorities().toArray()[0].toString().equals("ROLE_BODEGA")) {
				mav.setViewName("redirect:/kardex/list");
			}
		}
		mav.addObject("Home", ValidarDepartamento());
		return mav;
	}

	// Devuelve true si el usuario ha iniciado sesión
	boolean isUserLoggedIn() {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails;
	}

	/*
	 * @PostMapping("/agregar-producto") public String
	 * agregarProductoCarrito(HttpServletRequest request, @RequestParam("cantidad")
	 * int cantidad, @RequestParam("producto_id") int id) { ProductoCarrito
	 * productoCarrito = new ProductoCarrito(); Producto producto =
	 * productService.findById(id); productoCarrito.setProducto(producto);
	 * productoCarrito.setCantidad(cantidad);
	 * 
	 * HttpSession session = request.getSession(true); List<ProductoCarrito>
	 * productosSesion =
	 * (ArrayList<ProductoCarrito>)session.getAttribute("productosCarrito");
	 * 
	 * if(productosSesion!=null) { if(!productosSesion.isEmpty()) {
	 * for(ProductoCarrito pc : productosSesion) { if(id ==
	 * pc.getProducto().getIdArticulo()) { productosCarrito.remove(pc); break; } } }
	 * }
	 * 
	 * productosCarrito.add(productoCarrito);
	 * session.setAttribute("productosCarrito", productosCarrito);
	 * 
	 * return "redirect:/producto/index"; }
	 */

	@GetMapping("/agregar-producto/{cantidad}/{id}")
	public @ResponseBody String agregarProductoCarrito(HttpServletRequest request,
			@PathVariable("cantidad") int cantidad, @PathVariable("id") int id) {
		ProductoCarrito productoCarrito = new ProductoCarrito();
		Producto producto = productService.findById(id);
		productoCarrito.setProducto(producto);
		productoCarrito.setCantidad(cantidad);
		HttpSession session = request.getSession(true);
		List<ProductoCarrito> productosSesion = (ArrayList<ProductoCarrito>) session.getAttribute("productosCarrito");

		if (productosSesion != null) {
			if (!productosSesion.isEmpty()) {
				for (ProductoCarrito pc : productosSesion) {
					if (id == pc.getProducto().getIdArticulo()) {
						productosCarrito.remove(pc);
						break;
					}
				}
			}
		}

		productosCarrito.add(productoCarrito);
		session.setAttribute("productosCarrito", productosCarrito);
		return "OK";
	}

	@GetMapping("/remover-producto/{id}")
	public String removeProductoCarrito(@PathVariable("id") int id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<ProductoCarrito> productosSesion = (ArrayList<ProductoCarrito>) session.getAttribute("productosCarrito");

		for (ProductoCarrito pc : productosSesion) {
			if (id == pc.getProducto().getIdArticulo()) {
				productosSesion.remove(pc);

				break;
			}
		}

		return "redirect:/producto/index";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_VENTAS')")
	@GetMapping("/nuevo/{id}")
	public ModelAndView createProducto(@PathVariable("id") int idSubcategoria) {
		ModelAndView mav = new ModelAndView("producto/createProducto");

		mav.addObject("producto", new Producto());
		mav.addObject("kardex", new Kardex());
		mav.addObject("idSubcateogria", idSubcategoria);

		// user
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		mav.addObject("user", user.getUsername());
		mav.addObject("role", user.getAuthorities().toArray()[0].toString());

		return mav;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_VENTAS')")
	@PostMapping("/nuevo")
	public String storeProducto(@Valid @ModelAttribute("producto") Producto producto, BindingResult bindingResult,
			@ModelAttribute("kardex") Kardex kardex, @RequestParam("image") MultipartFile image,
			@RequestParam("idSubcategoria") int idSubcategoria, RedirectAttributes redirAttrs) {
		String path;
		Subcategoria subcategoria = subcategoriaService.getSubcategoria(idSubcategoria);

		if (bindingResult.hasErrors() || image.isEmpty()) {
			if (bindingResult.hasErrors()) {
				redirAttrs.addFlashAttribute("errors", bindingResult.getAllErrors());
			}
			if (image.isEmpty()) {
				redirAttrs.addFlashAttribute("imageError", "Debe seleccionar una imagen para el producto");
			}
			return "redirect:/producto/nuevo/" + subcategoria.getIdSubcategoria();
		} else {
			try {
				path = uploadImage(image);
				producto.setImagen(path);
				producto.setHabilitado(1);
				producto.setSubcategoria(subcategoria);
				productService.addProduct(producto);

				kardex.setProducto(producto);
				kardexService.addKardex(kardex);

			} catch (IOException e) {
				e.printStackTrace();
			}
			redirAttrs.addFlashAttribute("message", "Se ha registrado el producto con éxito");
			return "redirect:/producto/listar/" + subcategoria.getIdSubcategoria();
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_VENTAS')")
	@GetMapping("/actualizar/{id}")
	public ModelAndView editProducto(@PathVariable("id") int id) {
		ModelAndView mav = new ModelAndView("producto/updateProducto");
		Producto producto = productService.findById(id);
		Kardex kardex = kardexService.getKardex(producto.getKardex().getIdKardex());
		mav.addObject("kardex", kardex);
		mav.addObject("producto", producto);

		// user
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		mav.addObject("user", user.getUsername());
		mav.addObject("role", user.getAuthorities().toArray()[0].toString());

		return mav;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_VENTAS')")
	@PostMapping("/actualizar")
	public String updateProducto(@Valid @ModelAttribute("producto") Producto producto, BindingResult bindingResult,
			@ModelAttribute("kardex") Kardex kardex, @RequestParam("image") MultipartFile image,
			RedirectAttributes redirAttrs) {
		String path;
		Producto p = productService.findById(producto.getIdArticulo());
		Kardex k = kardexService.getKardex(kardex.getIdKardex());

		if (bindingResult.hasErrors()) {
			redirAttrs.addFlashAttribute("error", "Favor completar todos los campos");
			return "redirect:/producto/actualizar/" + p.getIdArticulo();
		} else {
			p.setTitulo(producto.getTitulo());
			p.setMarca(producto.getMarca());
			p.setMargenGanancia(producto.getMargenGanancia());
			p.setPorcentajeDescuento(producto.getPorcentajeDescuento());
			p.setDescripcionArticulo(producto.getDescripcionArticulo());

			k.setStockMinimo(kardex.getStockMinimo());
			k.setStockMaximo(kardex.getStockMaximo());

			if (image.isEmpty()) {
				p.setImagen(producto.getImagen());
				productService.updateProducto(p);
			} else {
				try {
					path = uploadImage(image);
					p.setImagen(path);
					productService.updateProducto(p);
					kardexService.addKardex(k);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			redirAttrs.addFlashAttribute("message", "Se ha actualizado el producto con éxito");
			return "redirect:/producto/listar/" + p.getSubcategoria().getIdSubcategoria();
		}
	}

	public String uploadImage(MultipartFile file) throws IOException {
		String UPLOAD_FOLDER = ".//src//main//resources//static//img_products//";

		byte[] bytes = file.getBytes();
		Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
		Files.write(path, bytes);

		return "/img_products/" + file.getOriginalFilename();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_VENTAS')")
	@GetMapping(path = { "/listar", "/listar/{id}" })
	public ModelAndView listProducto(@PathVariable("id") Optional<Integer> idSubcategoria) {
		ModelAndView mav = new ModelAndView("/producto/listProducto");
		List<Producto> productos = new ArrayList<Producto>();

		if (idSubcategoria.isPresent()) {
			Subcategoria subcategoria = subcategoriaService.getSubcategoria(idSubcategoria.get());
			productos = subcategoria.getProductos();
			mav.addObject("subcategoria", subcategoria);
		} else {
			productos = productService.getProductos();
			mav.addObject("todos", 1);
		}

		mav.addObject("productos", productos);

		// user
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		mav.addObject("user", user.getUsername());
		mav.addObject("role", user.getAuthorities().toArray()[0].toString());

		return mav;
	}

	@PostMapping("/asignar-descuento")
	public String asignarDescuento(HttpServletRequest request) {
		int producto_id = Integer.parseInt(request.getParameter("producto_id"));
		double descuento = Double.parseDouble(request.getParameter("descuento"));
		Producto producto = productService.findById(producto_id);
		producto.setPorcentajeDescuento(descuento);
		productService.updateProducto(producto);

		return "redirect:/producto/listar";
	}

	@PostMapping("/hab-deshab")
	public String habDeshabProducto(HttpServletRequest request) {
		int producto_id = Integer.parseInt(request.getParameter("producto_id_hab"));
		Producto producto = productService.findById(producto_id);

		if (producto.getHabilitado() == 1)
			producto.setHabilitado(0);
		else
			producto.setHabilitado(1);

		productService.updateProducto(producto);

		return "redirect:/producto/listar";
	}

	@GetMapping("/ver-detalle/{id}")
	public ModelAndView verDetalleProducto(@PathVariable("id") int id) {
		ModelAndView mav = new ModelAndView("producto/verDetalleProducto");
		Producto producto = productService.findById(id);
		mav.addObject("producto", producto);

		// user
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		mav.addObject("user", user.getUsername());
		mav.addObject("role", user.getAuthorities().toArray()[0].toString());

		return mav;
	}

	@GetMapping("/categoria/{id_cat}")
	public ModelAndView buscarCategoria(@PathVariable("id_cat") int id_cat) {
		ModelAndView mav = new ModelAndView("/producto/index");
		// Buscamos la Categoria
		Categoria cat = categoriaService.getCategoria(id_cat);
		mav.addObject("esProducto", 1);
		mav.addObject("Cat", cat);
		List<Subcategoria> sub_cats = cat.getSubcategorias();

		List<Producto> productos = new ArrayList<>();
		for (Subcategoria sub : sub_cats) {
			productos.addAll(sub.getProductos());
		}

		

		if(isUserLoggedIn()) {
	    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    UserDetails userDetail = (UserDetails) auth.getPrincipal();
		    mav.addObject("user", userDetail.getUsername());
			mav.addObject("role",userDetail.getAuthorities().toArray()[0].toString());
	    }
	    
		mav.addObject("departamentos", ValidarDepartamento());
		mav.addObject("productos", ValidarProductos(productos));
		mav.addObject("Cat", cat);

		return mav;
	}
	
	

	@GetMapping("/subcategoria/{id_subcat}")
	public ModelAndView buscarSubCat(@PathVariable("id_subcat") int id) {
		ModelAndView mav = new ModelAndView("/producto/index");
		// Buscamos la subCategoria
		Subcategoria sub_cat = subcategoriaService.getSubcategoria(id);
		List<Producto> productos = sub_cat.getProductos();
		
		mav.addObject("departamentos", ValidarDepartamento());
		mav.addObject("SubCat", sub_cat);
		mav.addObject("productos", ValidarProductos(productos));
		mav.addObject("esProducto", 1);

		if (isUserLoggedIn()) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			mav.addObject("user", userDetail.getUsername());
			mav.addObject("role", userDetail.getAuthorities().toArray()[0].toString());
		}

		return mav;
	}

	// Metodo que retorna los productos en existencia
	public List<Producto> ValidarProductos(List<Producto> productos) {

		List<Producto> productoContext = new ArrayList<>();

		for (Producto p : productos) {
			if (p.getHabilitado() == 1) {
				if (p.getSubcategoria().isHabilitado() == true) {
					if (p.getSubcategoria().getCategoria().isHabilitado() == true) {
						if (p.getSubcategoria().getCategoria().getDepartamento().isHabilitado() == true) {
							if (p.getKardex().getUnidadesDisponibles() != 0) {
								productoContext.add(p);
							}
						}
					}
				}
			}
		}
		return productoContext;
	}

	@RequestMapping(value = "/searchProduct", method = RequestMethod.GET)
	public String search(@RequestParam(value = "search", required = false) String q, Model model) {
		
		if(q.isEmpty()) {
			
			return "redirect:/producto/index";
		}
		
		List<Producto> searchResults = null;

		try {

			searchResults = productService.buscarProducto(q);

		} catch (Exception ex) {
			// here you should handle unexpected errors
			// ...
			// throw ex;
		}
		model.addAttribute("nombreBusqueda", q);
		model.addAttribute("esProducto", 1);
		if(searchResults.isEmpty()) {
			model.addAttribute("searchNull", 1);
		}
		else {
			model.addAttribute("productos", ValidarProductos(searchResults));
		}
		
		model.addAttribute("departamentos", ValidarDepartamento());
		if (isUserLoggedIn()) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			model.addAttribute("user", userDetail.getUsername());
			model.addAttribute("role", userDetail.getAuthorities().toArray()[0].toString());
		}
		return "/producto/index";
	}

	//Validador de los  departamentos
	public List<Departamento> ValidarDepartamento() {
		List<Departamento> departamentos = departamentoService.getDepartamentos();
		int countSubCat = 0, countProductos = 0, countCategorias = 0,countExistencia=0;	

		// Bucle para Departamento
		Iterator<Departamento> dep = departamentos.iterator();
		while (dep.hasNext()) {
			Departamento d = dep.next();

			// Si no hay categorias Eliminar el departamento
			if (d.getCategoria().isEmpty()) {
				dep.remove();
			}

			else {

				// Bucle para Categoria
				Iterator<Categoria> cat = d.getCategoria().iterator();
				while (cat.hasNext()) {
					Categoria cate = cat.next();
					// Si la Categoria no contiene subcategorias la elimina
					if (cate.getSubcategorias().isEmpty()) {
						cat.remove();
					} else {
						//Cuenta el numero de Categorias Desabilitadas
						if (cate.isHabilitado() == false) {
							countCategorias++;
						}
						// Bucle para SubCategoria
						Iterator<Subcategoria> sub_cat = cate.getSubcategorias().iterator();
						while (sub_cat.hasNext()) {
							Subcategoria sub = sub_cat.next();
							// Cuenta las subCategorias desabilitadas
							if (sub.isHabilitado() == false) {
								countSubCat++;
							}
							// Bucle para Producto
							Iterator<Producto> product = sub.getProductos().iterator();
							while (product.hasNext()) {
								Producto producto = product.next();
								// si la SubCategoria no tiene productos Elimina la subcategoria
								if (sub.getProductos().isEmpty()) {
									sub_cat.remove();
								} else {
									// cuenta el numero de productos desabilitados
									if (producto.getHabilitado() == 0) {
										countProductos++;
									}
									if(producto.getKardex().getUnidadesDisponibles()>0) {
										countExistencia++;
									}
								}
							}

							// Si el total de productos es igual al numero de productos desabilitados,
							// elimina la subcategoria
							if (sub.getProductos().size() == countProductos) {
								sub_cat.remove();
							}
							else {
								//Elimina la subcategoria si no encuentra existencias
								if(countExistencia==0) {
									sub_cat.remove();
								}
								countExistencia=0;
							}	
							
							countProductos = 0;

						}
						// Si el total de subcategorias es igual al numero de subcategorias
						// desabilitadas, elimina la categoria
						if (cate.getSubcategorias().size() == countSubCat) {

							cat.remove();
						}
						countSubCat = 0;
					}
				}
				// si el total de categorias es igual a la categorias desabilitadas Eliminara el
				// Departamento de la lista
				if (d.getCategoria().size() == countCategorias) {
					dep.remove();
				}
				countCategorias = 0;

			}

		}

		return departamentos;
	}

	
	@GetMapping("/departamento/{id_depar}")
	public ModelAndView buscarDepartamento(@PathVariable("id_depar") int id) {
		ModelAndView mav = new ModelAndView("/producto/index");
		
		Departamento depar=departamentoService.getDepartamento(id);
		List<Categoria> cat=depar.getCategoria();
		
		List<Producto> product= new ArrayList<>();
		for(Categoria c:cat) {
			for(Subcategoria sub:c.getSubcategorias()) {
				product.addAll(sub.getProductos());
			}
		}
		mav.addObject("esProducto", 1);
		mav.addObject("productos", ValidarProductos(product));
		mav.addObject("departamentos", ValidarDepartamento());
		mav.addObject("Dep", depar);
		
		if (isUserLoggedIn()) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			mav.addObject("user", userDetail.getUsername());
			mav.addObject("role", userDetail.getAuthorities().toArray()[0].toString());
		}
		return mav;
	}	
	

}
