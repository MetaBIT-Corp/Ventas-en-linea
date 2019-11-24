package com.metabit.ventasenlinea.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.metabit.ventasenlinea.entity.ArticuloPedido;
import com.metabit.ventasenlinea.entity.Cliente;
import com.metabit.ventasenlinea.entity.Cuenta;
import com.metabit.ventasenlinea.entity.Departamento;
import com.metabit.ventasenlinea.entity.Estado;
import com.metabit.ventasenlinea.entity.Kardex;
import com.metabit.ventasenlinea.entity.Pais;
import com.metabit.ventasenlinea.entity.Pedido;
import com.metabit.ventasenlinea.entity.ProductoCarrito;
import com.metabit.ventasenlinea.service.ArticuloPedidoService;
import com.metabit.ventasenlinea.service.ClienteService;
import com.metabit.ventasenlinea.service.CuentaService;
import com.metabit.ventasenlinea.service.EstadoService;
import com.metabit.ventasenlinea.service.KardexService;
import com.metabit.ventasenlinea.service.PaisService;
import com.metabit.ventasenlinea.service.PedidoService;
import com.metabit.ventasenlinea.service.UserService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

	public static final String PEDIDOS_CLIENTES = "pedido/listPedidoCliente";
	public static final String PEDIDOS_EMPLEADOS = "pedido/listPedidoEmployees";
	public static final String RESUMENCOMPRA = "pedido/resumenCompra";
	public static final String COMPROBANTE = "pedido/comprobanteCompra";
	public static final String METODO_PAGO = "pedido/metodoDePago";
	public static final String PAYPAL = "pedido/metodoDePagoPaypal";
	public static final String TARJETA = "pedido/metodoDePagoTarjeta";

	private static final Log LOG = LogFactory.getLog(PedidoController.class);

	@Autowired
	@Qualifier("pedidoServiceImpl")
	private PedidoService pedidoService;

	@Autowired
	@Qualifier("clienteServiceImpl")
	private ClienteService clienteService;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
	@Qualifier("estadoServiceImpl")
	private EstadoService estadoService;

	@Autowired
	@Qualifier("kardexServiceImpl")
	private KardexService kardexService;

	@Autowired
	@Qualifier("paisServiceImpl")
	private PaisService paisService;

	@Autowired
	@Qualifier("articuloPedidoServiceImpl")
	private ArticuloPedidoService articuloPedidoService;

	@Autowired
	@Qualifier("cuentaServiceImpl")
	private CuentaService cuentaService;

	// Se añade este Bean para poder comparar con la contraseña encriptada
	@Bean
	PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

	// No me funciona el PreAthorize
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/list-pedido")
	public ModelAndView viewListPedido() {

		ModelAndView mav = new ModelAndView(PEDIDOS_CLIENTES);

		// Obtener el cliente que tiene ese id de usario
		Cliente id_cliente = clienteService.BuscarUsuario(getUser());

		// Obtenemos el listado de pedidos
		List<Pedido> pedidos = pedidoService.getAllPedidosCliente(id_cliente);

		// Enviamos los pedidos a la vista y los montos totales de cada pedido
		mav.addObject("pedidos", pedidos);
		mav.addObject("montos", calcularMontos(pedidos));
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mav.addObject("user", user.getUsername());
		mav.addObject("role", user.getAuthorities().toArray()[0].toString());
		return mav;
	}
	
	@PreAuthorize("hasRole('ROLE_BODEGA') or hasRole('ROLE_VENTAS')")
	@GetMapping({ "/list", "/list/{estado}" })
	public ModelAndView viewListPedidoEmployees(@PathVariable(required = false) Integer estado) {
		ModelAndView mav = new ModelAndView(PEDIDOS_EMPLEADOS);

		// Recuperar el usuario logueado
		com.metabit.ventasenlinea.entity.User user = getUser();

		// Se obtiene el role
		User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String role = userDetails.getAuthorities().toArray()[0].toString();

		// Para recuperar todos
		if (estado == null) {
			estado = 0;
		}
		List<Pedido> pedidos = null;
		switch (estado) {
		case 1:
			// Listado de todos los enviados AUN NO SE QUE ID TENDRA ESE ESTADO
			pedidos = pedidoService.getAllPedidosEmploye(estadoService.getEstado(estado));
			break;
		case 2:
			// Listado de todos los pendientes AUN NO SE QUE ID TENDRA ESE ESTADO
			pedidos = pedidoService.getAllPedidosEmploye(estadoService.getEstado(estado));
			break;
		case 3:
			// Listado de todos los autorizados AUN NO SE QUE ID TENDRA ESE ESTADO
			pedidos = pedidoService.getAllPedidosEmploye(estadoService.getEstado(estado));
			break;
		case 0:
			// Todos sin importar estado
			pedidos = pedidoService.getAll();
		}

		// Enviamos los pedidos a la vista y los montos totales de cada pedido
		mav.addObject("pedidos", pedidos);
		mav.addObject("montos", calcularMontos(pedidos));
		mav.addObject("role", role);
		return mav;
	}

	@GetMapping("/{pedido}/resumen-compra")
	public ModelAndView viewResumenCompra(@PathVariable("pedido") int id_pedido) {
		ModelAndView mav = new ModelAndView(RESUMENCOMPRA);
		return resumen(mav, id_pedido);
	}

	@GetMapping("/{pedido}/comprobante-compra")
	public ModelAndView viewComprobanteCompra(@PathVariable("pedido") int id_pedido) {
		ModelAndView mav = new ModelAndView(COMPROBANTE);
		return resumen(mav, id_pedido);
	}

	/**
	 * Funcion que obtiene el resumen de un pedido que es utilzado tanto en el
	 * resumen de compra como en la generacion de comprobante.
	 * 
	 * @author ricardo
	 * @param mav       ModelAndView para agregar los parametros necesarios que se
	 *                  retornara
	 * @param id_pedido ID del pedido
	 * @return ModelAndView que se retornara
	 */
	public ModelAndView resumen(ModelAndView mav, int id_pedido) {
		// Se obtuvo en lista con el fin de usar el metodo calcularMontos
		List<Pedido> pedido = new ArrayList<Pedido>();
		pedido.add(pedidoService.getPedido(id_pedido));
		mav.addObject("pedido", pedidoService.getPedido(id_pedido));
		mav.addObject("monto", calcularMontos(pedido));
		// user
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mav.addObject("user", user.getUsername());
		mav.addObject("role", user.getAuthorities().toArray()[0].toString());
		return mav;
	}

	/**
	 * Obtiene el usuario logueado actualmente en el sistema
	 * 
	 * @return
	 */
	private com.metabit.ventasenlinea.entity.User getUser() {
		// Recuperar el usuario logueado
		com.metabit.ventasenlinea.entity.User user = null;
		if (isUserLoggedIn()) {
			User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			// Se busca el usuario por email que es unico
			user = userService.findByEmail(userDetails.getUsername());
		}
		return user;
	}

	/**
	 * Metodo que verifica si el usuario esta logueado
	 * 
	 * @return
	 */
	public boolean isUserLoggedIn() {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails;
	}

	/**
	 * Metodo que calcula el monto total de un pedido
	 * 
	 * @author Ricardo Estupinian
	 * @param pedidos Listado de pedidos del cliente
	 * @return
	 */
	private List<Double> calcularMontos(List<Pedido> pedidos) {
		// Recorremos la lista para sumar el monto de cada pedido
		List<Double> monto = new ArrayList<Double>();
		for (Pedido p : pedidos) {
			double m = 0;
			for (ArticuloPedido ap : p.getListaArticulo()) {
				m += ap.getPrecioUnitario() * ap.getCantidad();
			}
			monto.add(m);
		}

		return monto;
	}

	@PostMapping("/cambio-estado")
	public String cambiarEstadoPedido(HttpServletRequest request) {

		int pedido_id = Integer.parseInt(request.getParameter("pedido_id_cambio"));
		Pedido pedido = pedidoService.findById(pedido_id);
		int estado_actual = pedido.getEstado().getId_estado();
		// 1--Enviado
		// 2--Pendiente (Este estado es cuando el pedido ha sido pagado, pero ún no es
		// autorizado y menos enviado)
		// 3--Autorizado
		// 4--Solicitado (Este estado es cuando el pedido aún no ha sido pagado)
		Estado estado_nuevo;

		if (estado_actual == 2) {
			estado_nuevo = estadoService.getEstado(3);
		} else {
			estado_nuevo = estadoService.getEstado(1);
		}
		pedido.setEstado(estado_nuevo);
		pedidoService.updatePedido(pedido);

		return "redirect:/pedido/list";
	}

	// PAGO DE ARTICULOS
	/**
	 * Metodo que despliega un formulario para indicar los parametros de envío
	 * (pais, direccion de destino) además muestra las opciones de metodo de pago.
	 * si no se han indicado los parametros de envío estas aparecerán bloqueadas
	 * 
	 * @author Edwin Palacios
	 * @param id: id del pedido, si este ya fue creado
	 * @return
	 */
	
	@RequestMapping(path = { "/metodo-de-pago", "/metodo-de-pago/{id}" })
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	public String metodoDePago(@PathVariable("id") Optional<Integer> id, Model model) {
		if (id.isPresent()) {
			// activamos metodos de pago
			Integer num = Integer.valueOf(id.get());
			Pedido pedido = pedidoService.getPedido(num);
			if (pedido != null) {
				Pais pais = paisService.getPaisById(pedido.getPais().getIdPais());
				model.addAttribute("pedido", pedido);
				model.addAttribute("pais", pais);
			} else {
				model.addAttribute("paises", paisService.getAllPais());
			}

		} else {
			model.addAttribute("paises", paisService.getAllPais());
		}
		//user
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user", user.getUsername());
		model.addAttribute("role", user.getAuthorities().toArray()[0]);
		return METODO_PAGO;
	}

	/**
	 * método para recibir el post del formulario de parametros de envio. el cual
	 * permite crear el pedido
	 * 
	 * @author Edwin Palacios
	 * @param id_pais
	 * @param direccionDestino
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@PostMapping("/metodo-de-pago/envio-post")
	public String createPedidoPost(@RequestParam("id_pais") int idPais,
			@RequestParam("direccion_destino") String direccionDestino) {
		if (direccionDestino.isEmpty()) {
			LOG.info("INFORMACION" + idPais + " " + direccionDestino);
			return "redirect:/producto/index";
		} else {
			// obtenemos cliente
			com.metabit.ventasenlinea.entity.User user = getUser();
			Cliente cliente = clienteService.BuscarUsuario(user);
			// obtenemos pais
			Pais pais = paisService.getPaisById(idPais);

			// obtenemos estado
			Estado estado = estadoService.getEstado(4);

			// creamos pedido
			Pedido pedido = new Pedido();
			pedido.setDireccionDestino(direccionDestino);
			pedido.setFechaPedido(new Date());
			pedido.setEstado(estado);
			pedido.setCliente(cliente);
			pedido.setPais(pais);
			pedidoService.createPedido(pedido);
			LOG.info("INFORMACION: " + pedido.getIdPedido());
			return "redirect:/pedido/metodo-de-pago/" + pedido.getIdPedido();
		}

	}

	/**
	 * método para desplegar el formulario de pago con paypal
	 * 
	 * @author Edwin Palacios
	 * @param HttpServletRequest request: carrito de compras
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/metodo-de-pago/paypal")
	public ModelAndView metodoDePagoPaypal(HttpServletRequest request) {
		float totalAPagar = 0.0f;
		ModelAndView mav = new ModelAndView(PAYPAL);
		// Obtenemos productos de carrito de compra
		HttpSession session = request.getSession();
		List<ProductoCarrito> productosCarritos = (ArrayList<ProductoCarrito>) session.getAttribute("productosCarrito");
		if (productosCarritos != null) {
			totalAPagar = totalAPagar(productosCarritos);
		} else {
			mav.addObject("no", true);
		}

		LOG.info("TOTAL: " + totalAPagar);
		mav.addObject("total", String.format("%.2f", totalAPagar));
		// user
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mav.addObject("user", user.getUsername());
		mav.addObject("role", user.getAuthorities().toArray()[0]);
		return mav;
	}

	/**
	 * método que recibe el post de paypal
	 * 
	 * @author Edwin Palacios
	 * @param HttpServletRequest request: carrito de compras
	 * @param email
	 * @param password
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@PostMapping("/metodo-de-pago/paypal/post")
	public String metodoDePagoPaypalPost(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpServletRequest request, RedirectAttributes redirectAttrs) {

		// total a pagar de todo el pedido
		float totalAPagar = 0.0f;

		// obtenemos el usuario loggeado
		com.metabit.ventasenlinea.entity.User user = getUser();

		// Si el email y la contraseña son válidos
		if (user.getEmail().equals(email) && getEncoder().matches(password, user.getPassword())) {

			// Obtenemos pedido
			Pedido pedido = pedidoService.getUltimoPedido();
			LOG.info(pedido.toString());

			// Obtenemos productos de carrito de compra
			HttpSession session = request.getSession();
			List<ProductoCarrito> productosCarritos = (ArrayList<ProductoCarrito>) session
					.getAttribute("productosCarrito");

			if (productosCarritos != null) {

				// Obtenemos cuenta
				Cuenta cuenta = cuentaService.getCuenta(clienteService.BuscarUsuario(user));

				// verificamos si posee lo necesario para pagar
				if (cuenta.getSaldo() > totalAPagar(productosCarritos)) {

					for (ProductoCarrito pc : productosCarritos) {

						Kardex kardex = kardexService.getKardexByProducto(pc.getProducto());
						float precioSinCombros = 0.0f;
						float totalConCobros = 0.0f;
						precioSinCombros = (float) (kardex.getCostoUnitario()
								+ kardex.getCostoUnitario() * (pc.getProducto().getMargenGanancia()/100));

						totalConCobros = (float) (
								precioSinCombros
								+ precioSinCombros * pedido.getPais().getCostoEnvio()
								+ precioSinCombros * pedido.getPais().getImpuesto()
								- precioSinCombros * (pc.getProducto().getPorcentajeDescuento()/100));
						// Creamos ArticuloPedido
						ArticuloPedido ap = new ArticuloPedido();
						ap.setCantidad(pc.getCantidad());
						ap.setPedido(pedido);
						ap.setPrecioUnitario(totalConCobros);
						ap.setProducto(pc.getProducto());
						articuloPedidoService.createArticuloPedido(ap);

						totalConCobros *= pc.getCantidad();
						totalAPagar += totalConCobros;

					}
					// disminuir a cuenta
					cuenta.setSaldo(cuenta.getSaldo()- totalAPagar);
					LOG.info("SALDOOOOO: " + cuenta.getSaldo());
					cuentaService.createCuenta(cuenta);
					//borramos carrito
					productosCarritos.removeAll(productosCarritos);
					//cambiar estado
					Estado estado = estadoService.getEstado(2);
					pedido.setEstado(estado);
					pedidoService.updatePedido(pedido);
					return "redirect:/pedido/" +pedido.getIdPedido()+ "/comprobante-compra";

				} else {
					redirectAttrs.addFlashAttribute("mensaje", "El saldo en su cuenta es insuficiente.")
							.addFlashAttribute("clase", "danger");
				}

			} else {
				redirectAttrs.addFlashAttribute("mensaje", "No ha agregado productos en el carro de compra.")
						.addFlashAttribute("clase", "danger");
			}

		} else {
			redirectAttrs
					.addFlashAttribute("mensaje", "El email y/o la contraseña no son válidos, por favor verificar.")
					.addFlashAttribute("clase", "danger");

		}
		return "redirect:/pedido/metodo-de-pago/paypal";
	}

	/**
	 * método para desplegar el formulario de pago con tarjeta
	 * 
	 * @author Edwin Palacios
	 * @param HttpServletRequest request: carrito de compras
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/metodo-de-pago/tarjeta")
	public ModelAndView metodoDePagoTarjeta(HttpServletRequest request) {
		LOG.info("Aqui -------------------------------------- 1 ");
		float totalAPagar = 0.0f;
		ModelAndView mav = new ModelAndView(TARJETA);
		// Obtenemos productos de carrito de compra
		LOG.info("Aqui -------------------------------------- 2 ");
		HttpSession session = request.getSession();
		LOG.info("Aqui -------------------------------------- 3 ");
		List<ProductoCarrito> productosCarritos = (ArrayList<ProductoCarrito>) session.getAttribute("productosCarrito");
		if (productosCarritos != null) {
			totalAPagar = totalAPagar(productosCarritos);
		} else {
			mav.addObject("no", true);
		}

		LOG.info("TOTAL: " + totalAPagar);
		mav.addObject("total", String.format("%.2f", totalAPagar));
		// user
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mav.addObject("user", user.getUsername());
		mav.addObject("role", user.getAuthorities().toArray()[0]);
		return mav;
	}

	/**
	 * método para recibir el post del formulario de pago con tarjeta
	 * 
	 * @author Edwin Palacios
	 * @param HttpServletRequest request: carrito de compras
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@PostMapping("/metodo-de-pago/tarjeta/post")
	public String metodoDePagoTarjetaPost(@RequestParam("numero") String numero,
			@RequestParam("fecha") String fechaExpiracion, @RequestParam("codigo") String codigo,
			@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido,
			HttpServletRequest request, RedirectAttributes redirectAttrs) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		

		try {
			Date date = formatter.parse(fechaExpiracion);

			// total a pagar de todo el pedido
			float totalAPagar = 0.0f;

			// obtenemos el usuario loggeado
			com.metabit.ventasenlinea.entity.User user = getUser();
			
			//obtenemos cliente
			
			Cliente cliente = clienteService.BuscarUsuario(user);
			LOG.info(cliente.getNombreCliente());
			LOG.info(nombre);
			// Obtenemos cuenta
			Cuenta cuenta = cuentaService.getCuenta(cliente);
			//validacion 
			//el metodo compareTo da como resultado un 1 si la fecha es mayor, un 0 si las fechas son iguales o un -1 si la fecha es menor.
			if(cuenta.getCodigo() == Integer.parseInt(codigo) && 
					cuenta.getNumeroTarjeta().equals(numero) && 
					cuenta.getFechaDeVencimiento().compareTo(date) == 1 
					) {
				
				LOG.info(cuenta.getFechaDeVencimiento());
				
				// Obtenemos pedido
				Pedido pedido = pedidoService.getUltimoPedido();
				LOG.info(pedido.toString());
				
				// Obtenemos productos de carrito de compra
				HttpSession session = request.getSession();
				List<ProductoCarrito> productosCarritos = (ArrayList<ProductoCarrito>) session
						.getAttribute("productosCarrito");
				
				if (productosCarritos != null) {

					// verificamos si posee lo necesario para pagar
					if (cuenta.getSaldo() > totalAPagar(productosCarritos)) {
						

						for (ProductoCarrito pc : productosCarritos) {

							Kardex kardex = kardexService.getKardexByProducto(pc.getProducto());
							float precioSinCombros = 0.0f;
							float totalConCobros = 0.0f;
							precioSinCombros = (float) (kardex.getCostoUnitario()
									+ kardex.getCostoUnitario() * (pc.getProducto().getMargenGanancia()/100));

							totalConCobros = (float) (
									  precioSinCombros
									+ precioSinCombros * pedido.getPais().getCostoEnvio()
									+ precioSinCombros * pedido.getPais().getImpuesto()
									- precioSinCombros * (pc.getProducto().getPorcentajeDescuento()/100));
							// Creamos ArticuloPedido
							ArticuloPedido ap = new ArticuloPedido();
							ap.setCantidad(pc.getCantidad());
							ap.setPedido(pedido);
							ap.setPrecioUnitario(totalConCobros);
							ap.setProducto(pc.getProducto());
							articuloPedidoService.createArticuloPedido(ap);

							totalConCobros *= pc.getCantidad();
							totalAPagar += totalConCobros;

						}
						// disminuir a cuenta
						cuenta.setSaldo(cuenta.getSaldo() - totalAPagar);
						cuentaService.createCuenta(cuenta);
						
						//borramos carrito
						productosCarritos.removeAll(productosCarritos);
						
						//cambiar estado
						Estado estado = estadoService.getEstado(2);
						pedido.setEstado(estado);
						pedidoService.updatePedido(pedido);
						
						return "redirect:/pedido/" +pedido.getIdPedido()+ "/comprobante-compra";

					} else {
						redirectAttrs.addFlashAttribute("mensaje", "El saldo en su cuenta es insuficiente.")
								.addFlashAttribute("clase", "danger");
					}

				} else {
					redirectAttrs.addFlashAttribute("mensaje", "No ha agregado productos en el carro de compra.")
							.addFlashAttribute("clase", "danger");
				}
			}else {
				redirectAttrs
				.addFlashAttribute("mensaje", "Los datos no son válidos, por favor verificar.")
				.addFlashAttribute("clase", "danger");
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "redirect:/pedido/metodo-de-pago/tarjeta";
	}

	public void llenarBdPais() {
		// vaciamos los paises
		paisService.deleteAllPais();

		// creamos paises
		ArrayList<String> nombresPais = new ArrayList<String>();
		nombresPais.add("Antigua Y Barbuda");
		nombresPais.add("Argentina");
		nombresPais.add("Bahamas");
		nombresPais.add("Barbados");
		nombresPais.add("Belice");
		nombresPais.add("Bolivia");
		nombresPais.add("Brasil");
		nombresPais.add("Cánada");
		nombresPais.add("Chile");
		nombresPais.add("Colombia");
		nombresPais.add("Costa Rica");
		nombresPais.add("Cuba");
		nombresPais.add("Dominicana");
		nombresPais.add("Ecuador");
		nombresPais.add("El Salvador");
		nombresPais.add("Estados Unidos");
		nombresPais.add("Granada");
		nombresPais.add("Guatemala");
		nombresPais.add("Guyana");
		nombresPais.add("Haití");
		nombresPais.add("Honduras");
		nombresPais.add("Jamaica");
		nombresPais.add("México");
		nombresPais.add("Nicaragua");
		nombresPais.add("Pánama");
		nombresPais.add("Paraguay");
		nombresPais.add("Perú");
		nombresPais.add("República Dominicana");
		nombresPais.add("San Cristóbal y Nieves");
		nombresPais.add("San Vicente y las Granadinas");
		nombresPais.add("Santa Lucia");
		nombresPais.add("Surinam");
		nombresPais.add("Trinidad y Tobago");
		nombresPais.add("Uruguay");
		nombresPais.add("Venezuela");

		for (String pais : nombresPais) {
			Pais paisNuevo = new Pais();
			paisNuevo.setNombrePais(pais);
			paisNuevo.setCostoEnvio((float) (Math.random()));
			paisNuevo.setImpuesto((float) (Math.random()));
			paisService.createPais(paisNuevo);
		}

	}

	public float totalAPagar(List<ProductoCarrito> productosCarritos) {
		float total = 0.0f;
		// Obtenemos pedido
		Pedido pedido = pedidoService.getUltimoPedido();
		LOG.info(pedido.toString());

		if (productosCarritos != null) {
			for (ProductoCarrito pc : productosCarritos) {

				Kardex kardex = kardexService.getKardexByProducto(pc.getProducto());
				float precioSinCombros = 0.0f;
				float totalConCobros = 0.0f;
				precioSinCombros = (float) (kardex.getCostoUnitario()
						+ kardex.getCostoUnitario() * (pc.getProducto().getMargenGanancia()/100));
				LOG.info("PRECIO SIN COBROS------------" + precioSinCombros);
				totalConCobros = (float) (
						precioSinCombros 
						+ precioSinCombros * pedido.getPais().getCostoEnvio()
						+ precioSinCombros * pedido.getPais().getImpuesto()
						- precioSinCombros * (pc.getProducto().getPorcentajeDescuento()/100));
				LOG.info("PRECIO CON COBROS------------" + totalConCobros);
				totalConCobros *= pc.getCantidad();
				LOG.info("TOTAL CON COBROS------------" + totalConCobros);
				total += totalConCobros;
			}
			

		}
		LOG.info("TOTAL TOTAL------------" + total);
		return total;
	}
}
