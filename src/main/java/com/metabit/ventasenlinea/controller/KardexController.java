package com.metabit.ventasenlinea.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.metabit.ventasenlinea.entity.Kardex;
import com.metabit.ventasenlinea.service.KardexService;

@Controller
@RequestMapping("/kardex")
public class KardexController {
	
	private static final String INDEX_KARDEX="kardex/listKardex";
	
	@Autowired
	@Qualifier("kardexServiceImpl")
	private KardexService kardexService;
	
	@GetMapping("/list")
	public ModelAndView indexKardex() {
		ModelAndView mav=new ModelAndView(INDEX_KARDEX);
		mav.addObject("listado", kardexService.getAllKardex());
		return mav;
	}
	
	//Se utilizara esto para llenar el datatable de manera asincrona.
	/*@GetMapping("/api-all")
	public @ResponseBody List<Kardex> getAllKardex(){
		//Se utiliza response body porque lo convierte de manera automatica a json
		return kardexService.getAllKardex();
	}*/
}
