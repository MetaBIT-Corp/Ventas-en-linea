package com.metabit.ventasenlinea.controller;

import java.util.List;
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
import com.metabit.ventasenlinea.entity.Subcategoria;
import com.metabit.ventasenlinea.service.CategoriaService;
import com.metabit.ventasenlinea.service.SubcategoriaService;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/departamento/categoria/{id_categoria}/subcategoria")
public class SubcategoriaController {
	// importamos servicios
	@Autowired
	@Qualifier("subcategoriaServiceImpl")
	private SubcategoriaService subcategoriaService;

	@Autowired
	@Qualifier("categoriaServiceImpl")
	private CategoriaService categoriaService;

	// Listado de subcategorias
	private static final String INDEX_VIEW = "subcategoria/index";
	private static final String CREATE_SUB_VIEW = "subcategoria/crearSubcategoria";

	@GetMapping("/listar")
	public ModelAndView index(@PathVariable("id_categoria") int idCategoria) {
		Categoria categoria = categoriaService.getCategoria(idCategoria);
		List<Subcategoria> subcategorias = subcategoriaService.listAllSubcategoriasByCategoria(categoria);
		ModelAndView mav = new ModelAndView(INDEX_VIEW);
		mav.addObject("subcategorias", subcategorias);
		mav.addObject("categoria", categoria);
		// user
		// user
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mav.addObject("user", user.getUsername());
		mav.addObject("role", user.getAuthorities().toArray()[0].toString());
		return mav;
	}

	// método para desplegar el formulario de crear formulario
	@RequestMapping(path = { "/form-subcategoria", "/form-subcategoria/{id}" })
	public ModelAndView crearSubcategoria(@PathVariable("id_categoria") int idCategoria,
			@PathVariable("id") Optional<Integer> id) {
		ModelAndView mav = new ModelAndView(CREATE_SUB_VIEW);
		if (id.isPresent()) {
			// actualizamos
			Integer num = Integer.valueOf(id.get());
			Subcategoria subcategoria = subcategoriaService.getSubcategoria(num);
			mav.addObject("subcategoria", subcategoria);
		} else {
			// departamento empty para crear departamentos
			mav.addObject("subcategoria", new Subcategoria());
		}
		mav.addObject("id_categoria", idCategoria);
		// user
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mav.addObject("user", user.getUsername());
		mav.addObject("role", user.getAuthorities().toArray()[0].toString());
		return mav;
	}

	// método para recibir el post del formulario de crear departamento
	@PostMapping("/crear-subcategoria-post")
	public String createCategoriaPost(@Valid @ModelAttribute("subcategoria") Subcategoria subcategoria,
			BindingResult bindingResult, @PathVariable("id_categoria") int idCategoria) {
		if (bindingResult.hasErrors()) {
			return "subcategoria/crearSubcategoria";
		} else {
			Categoria categoria = categoriaService.getCategoria(idCategoria);
			subcategoria.setCategoria(categoria);
			if (subcategoria.getIdSubcategoria() == 0) {
				subcategoria.setHabilitado(true);
			}
			subcategoriaService.createSubcategoria(subcategoria);
			return "redirect:/departamento/categoria/" + idCategoria + "/subcategoria/listar";
		}

	}

	// método para recibir el post de deshabilitar subcategoria post
	@PostMapping("/deshabilitar-subcategoria-post")
	public String deshabilitarSubcategoriaPost(@RequestParam("id_subcategoria") int idSubcategoria,
			@PathVariable("id_categoria") int idCategoria) {
		Subcategoria subcategoria = subcategoriaService.getSubcategoria(idSubcategoria);
		subcategoria.setHabilitado(false);
		subcategoriaService.updateSubcategoria(subcategoria);
		return "redirect:/departamento/categoria/" + idCategoria + "/subcategoria/listar";

	}

	// método para recibir el post de habilitar subcategoria post
	@PostMapping("/habilitar-subcategoria-post")
	public String habilitarSubcategoriaPost(@RequestParam("id_subcategoria") int idSubcategoria,
			@PathVariable("id_categoria") int idCategoria) {
		Subcategoria subcategoria = subcategoriaService.getSubcategoria(idSubcategoria);
		subcategoria.setHabilitado(true);
		subcategoriaService.updateSubcategoria(subcategoria);
		return "redirect:/departamento/categoria/" + idCategoria + "/subcategoria/listar";
	}

}
