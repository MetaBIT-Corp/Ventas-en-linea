package com.metabit.ventasenlinea.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.metabit.ventasenlinea.entity.ArticuloPedido;
import com.metabit.ventasenlinea.entity.Cliente;
import com.metabit.ventasenlinea.entity.Pedido;
import com.metabit.ventasenlinea.service.ClienteService;
import com.metabit.ventasenlinea.service.PedidoService;
import com.metabit.ventasenlinea.service.UserService;

import sun.net.www.protocol.http.AuthCache;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

	public static final String PEDIDOS_CLIENTES = "pedido/listPedidoCliente";
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

	@GetMapping("/list-pedido")
	public ModelAndView viewListPedido() {

		ModelAndView mav = new ModelAndView(PEDIDOS_CLIENTES);

		// Recuperar el usuario logueado
		com.metabit.ventasenlinea.entity.User user = null;
		if (isUserLoggedIn()) {
			User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			// Se busca el usuario por email que es unico
			user = userService.findByEmail(userDetails.getUsername());
		}

		// Obtener el cliente que tiene ese id de usario
		Cliente id_cliente = clienteService.BuscarUsuario(user);

		// Obtenemos el listado de pedidos
		List<Pedido> pedidos = pedidoService.getAllPedidosCliente(id_cliente);

		// Enviamos los pedidos a la vista y los montos totales de cada pedido
		mav.addObject("pedidos", pedidos);
		mav.addObject("montos", calcularMontos(pedidos));
		return mav;
	}

	public boolean isUserLoggedIn() {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails;
	}

	private List<Double> calcularMontos(List<Pedido> pedidos) {
		// Recorremos la lista para sumar el monto de cada pedido
		List<Double> monto = new ArrayList<Double>();
		for (Pedido p : pedidos) {
			double m = 0;
			for (ArticuloPedido ap : p.getListaArticulo()) {
				m += ap.getPrecio_unitario()*ap.getCantidad();
			}
			LOG.info("VALOOOOOOOOOOR "+m);
			monto.add(m);
		}
		
		return monto;
	}
}
