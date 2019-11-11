package com.metabit.ventasenlinea.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.metabit.ventasenlinea.entity.Kardex;
import com.metabit.ventasenlinea.service.EntradaSalidaService;
import com.metabit.ventasenlinea.service.KardexService;

@Controller
@RequestMapping("/kardex-one")
public class EntradaSalidaController {
	
	private static final String LIST_ES="/kardex/listEntradaSalida";
	
	@Autowired
	@Qualifier("entradaSalidaServiceImpl")
	private EntradaSalidaService entradaSalidaService;
	
	@Autowired
	@Qualifier("kardexServiceImpl")
	private KardexService kardexService;
	
	@GetMapping("/{kardex}/entrada-salida")
	public ModelAndView indexKardex(@PathVariable("kardex")int kardex_id) {
		ModelAndView mav=new ModelAndView(LIST_ES);
		Kardex kardex=kardexService.getKardex(kardex_id);
		mav.addObject("kardex", kardex);
		mav.addObject("listado", entradaSalidaService.getAllEntradaSalida(kardex));
		return mav;
	}

}
