package com.metabit.ventasenlinea.controller;

import java.util.Date;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.metabit.ventasenlinea.entity.EntradaSalida;
import com.metabit.ventasenlinea.entity.Kardex;
import com.metabit.ventasenlinea.service.EntradaSalidaService;
import com.metabit.ventasenlinea.service.KardexService;

@Controller
@RequestMapping("/kardex-one")
public class EntradaSalidaController {

	private static final String LIST_ES = "/kardex/listEntradaSalida";
	private static final String REGISTER_ES = "/kardex/indexRegisterEntradaSalida";
	private static final Log LOG = LogFactory.getLog(EntradaSalidaController.class);

	@Autowired
	@Qualifier("entradaSalidaServiceImpl")
	private EntradaSalidaService entradaSalidaService;

	@Autowired
	@Qualifier("kardexServiceImpl")
	private KardexService kardexService;

	@GetMapping("/{kardex}/entrada-salida")
	public ModelAndView indexKardex(@PathVariable("kardex") int kardex_id) {
		ModelAndView mav = new ModelAndView(LIST_ES);
		Kardex kardex = kardexService.getKardex(kardex_id);
		mav.addObject("kardex", kardex);
		mav.addObject("es", new EntradaSalida());
		mav.addObject("listado", entradaSalidaService.getAllEntradaSalida(kardex));
		return mav;
	}

	@PostMapping("/{kar}/addES")
	public String addEntradaSalida(@PathVariable("kar") int kardex_id,
			@Valid @ModelAttribute(name = "es") EntradaSalida es, BindingResult result, RedirectAttributes attrs) {
		String ruta = "redirect:/kardex-one/" + kardex_id + "/entrada-salida";
		if (result.hasErrors()) {
			attrs.addFlashAttribute("errors", result.getAllErrors());
			return ruta;
		}
		Kardex kardex = kardexService.getKardex(kardex_id);

		// Se agrega la fecha actual del sistema
		Date fecha = new Date();
		es.setKardex(kardex);
		es.setFecha(fecha);

		double denominador = kardex.getUnidadesDisponibles() + es.getCantidad();

		// Se actualiza el valor de unidades disponibles de kardex
		if (es.getIsEntradaSalida()) {

			// Se verifica que no se saque mas de lo que hay en stock
			if (kardex.getUnidadesDisponibles()-es.getCantidad()  < 0) {
				kardex.setUnidadesDisponibles(es.getCantidad() - kardex.getUnidadesDisponibles());
				attrs.addFlashAttribute("message", "No se puede sacar mas de lo que hay en existencia.");
				return ruta;
			}
			denominador = kardex.getUnidadesDisponibles() - es.getCantidad();
		}
		
		// Se valida que no sobrepase el stock maximo
		if ((kardex.getUnidadesDisponibles() + es.getCantidad()) > kardex.getStockMaximo()) {
			attrs.addFlashAttribute("message", "Se sobrepasa el stock maximo");
			return ruta;
		}
		
		kardex.setUnidadesDisponibles(es.getCantidad() + kardex.getUnidadesDisponibles());

		// Calcular costo promedio (no recuerdo si es asi faltaria revisar)
		if (kardex.getCostoUnitario() == 0) {
			kardex.setCostoUnitario(es.getPrecio());
		} else {
			double numerador = (kardex.getUnidadesDisponibles() * kardex.getCostoUnitario())
					* (es.getCantidad() * es.getPrecio());
			kardex.setCostoUnitario(numerador / denominador);
		}

		// Se guardan ambos modelos
		kardexService.addKardex(kardex);
		entradaSalidaService.addEntradaSalida(es);
		return ruta;
	}

}
