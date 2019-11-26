package com.metabit.ventasenlinea.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.metabit.ventasenlinea.entity.Categoria;
import com.metabit.ventasenlinea.entity.Departamento;
import com.metabit.ventasenlinea.service.CategoriaService;
import com.metabit.ventasenlinea.service.DepartamentoService;

@Controller

@RequestMapping("/departamento/{id_departamento}/categoria")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CategoriaController {

	@Autowired
	@Qualifier("categoriaServiceImpl")
	private CategoriaService categoriaService;

	@Autowired
	@Qualifier("departamentoServiceImpl")
	private DepartamentoService departamentoService;

	private static final String INDEX_VIEW = "categoria/index";
	private static final String CREAR_CATEGORIA_VIEW = "categoria/crearCategoria";

	
	@GetMapping("/listar")
	public ModelAndView index(@PathVariable("id_departamento") int idDepartamento) {
		Departamento departamento = departamentoService.getDepartamento(idDepartamento);
		ModelAndView mav = new ModelAndView(INDEX_VIEW);
		mav.addObject("categorias", categoriaService.getCategorias(departamento));
		mav.addObject("departamento", departamento);
		// user
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mav.addObject("user", user.getUsername());
		mav.addObject("role", user.getAuthorities().toArray()[0].toString());
		return mav;
	}

	@RequestMapping(path = {"/form-categoria", "/form-categoria/{id}"})
	public ModelAndView crearCategoria(@PathVariable("id_departamento") int idDepartamento,@PathVariable("id") Optional<Integer> id) {
		ModelAndView mav = new ModelAndView(CREAR_CATEGORIA_VIEW);
		if(id.isPresent()) {
			//actualizamos
			Integer num = Integer.valueOf(id.get());
			Categoria categoria = categoriaService.getCategoria(num);
			mav.addObject("categoria", categoria);
		}else {
			// departamento empty para crear departamentos
			mav.addObject("categoria", new Categoria());
		}
		mav.addObject("id_departamento", idDepartamento);
		// user
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mav.addObject("user", user.getUsername());
		mav.addObject("role", user.getAuthorities().toArray()[0].toString());
		return mav;
	}

	// método para recibir el post del formulario de crear departamento
	@PostMapping("/crear-categoria-post")
	public String createCategoriaPost(@Valid @ModelAttribute("categoria") Categoria categoria,
			BindingResult bindingResult, @PathVariable("id_departamento") int idDepartamento) {
		if (bindingResult.hasErrors()) {
			return "categoria/crearCategoria";
		} else {
			Departamento departamento = departamentoService.getDepartamento(idDepartamento);
			categoria.setDepartamento(departamento);
			if(categoria.getId() == 0) {
				categoria.setHabilitado(true);
			}
			categoriaService.createCategoria(categoria);
			return "redirect:/departamento/"+idDepartamento+"/categoria/listar";
		}

	}
	
	// método para recibir el post de deshabilitar categoria post
	@PostMapping("/deshabilitar-categoria-post")
	public String deshabilitarCategoriaPost(@RequestParam("id_categoria") int idCategoria,
			@PathVariable("id_departamento") int idDepartamento) {
		Categoria categoria = categoriaService.getCategoria(idCategoria);
		categoria.setHabilitado(false);
		categoriaService.updateCategoria(categoria);
		return "redirect:/departamento/" + idDepartamento + "/categoria/listar";

	}

	// método para recibir el post de habilitar departamento post
	@PostMapping("/habilitar-categoria-post")
	public String habilitarCategoriaPost(@RequestParam("id_categoria") int idCategoria,
			@PathVariable("id_departamento") int idDepartamento) {
		Categoria categoria = categoriaService.getCategoria(idCategoria);
		categoria.setHabilitado(true);
		categoriaService.updateCategoria(categoria);
		return "redirect:/departamento/" + idDepartamento + "/categoria/listar";

	}
}
