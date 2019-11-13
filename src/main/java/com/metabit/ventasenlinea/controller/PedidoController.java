package com.metabit.ventasenlinea.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.metabit.ventasenlinea.entity.ArticuloPedido;
import com.metabit.ventasenlinea.entity.Cliente;
import com.metabit.ventasenlinea.entity.Estado;
import com.metabit.ventasenlinea.entity.Pedido;
import com.metabit.ventasenlinea.service.ClienteService;
import com.metabit.ventasenlinea.service.EstadoService;
import com.metabit.ventasenlinea.service.PedidoService;
import com.metabit.ventasenlinea.service.UserService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

	public static final String PEDIDOS_CLIENTES = "pedido/listPedidoCliente";
	public static final String PEDIDOS_EMPLEADOS = "pedido/listPedidoEmployees";
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
	
	//No me funciona el PreAthorize
	//@PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('ROLE_VENTAS')")
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
		return mav;
	}

	@GetMapping({ "/list", "/list/{estado}" })
	public ModelAndView viewListPedidoEmployees(@PathVariable(required = false) Integer estado) {
		ModelAndView mav = new ModelAndView(PEDIDOS_EMPLEADOS);
		
		// Recuperar el usuario logueado
		com.metabit.ventasenlinea.entity.User user = getUser();

		// Se obtiene el role
		User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String role = userDetails.getAuthorities().toArray()[0].toString();
		
		//Para recuperar todos
		if(estado==null) {
			estado=0;
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
			//Todos sin importar estado
			pedidos=pedidoService.getAll();
		}
		
		// Enviamos los pedidos a la vista y los montos totales de cada pedido
		mav.addObject("pedidos", pedidos);
		mav.addObject("montos", calcularMontos(pedidos));
		mav.addObject("role", role);
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
		//1--Enviado
		//2--Pendiente
		//3--Autorizado
		Estado estado_nuevo;
		
		if(estado_actual == 2) {
			estado_nuevo = estadoService.getEstado(3);
		}else {
			estado_nuevo = estadoService.getEstado(1);
		}
		pedido.setEstado(estado_nuevo);
		pedidoService.updatePedido(pedido);
		
		return "redirect:/pedido/list";
	}
}
