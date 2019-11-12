package com.metabit.ventasenlinea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.metabit.ventasenlinea.entity.Departamento;
import com.metabit.ventasenlinea.service.CategoriaService;
import com.metabit.ventasenlinea.service.DepartamentoService;

@Controller
@RequestMapping("/departamento/{id_departamento}/categoria")
public class CategoriaController {

	@Autowired
	@Qualifier("categoriaServiceImpl")
	private CategoriaService categoriaService;
	
	@Autowired
	@Qualifier("departamentoServiceImpl")
	private DepartamentoService departamentoService;
	
	private static final String INDEX_VIEW = "categoria/index";
	
	@GetMapping("/listar")
	public ModelAndView index(@PathVariable("id_departamento") int idDepartamento) {
		Departamento departamento = departamentoService.getDepartamento(idDepartamento);
		ModelAndView mav = new ModelAndView(INDEX_VIEW);
		mav.addObject("categorias", categoriaService.getCategorias(departamento));
		mav.addObject("departamento", departamento);
		//user
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mav.addObject("user", user.getUsername());
		mav.addObject("role", user.getAuthorities().toArray()[0]);
		return mav;
	}
}
