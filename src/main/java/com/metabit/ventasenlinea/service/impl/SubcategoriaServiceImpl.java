package com.metabit.ventasenlinea.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.metabit.ventasenlinea.entity.Categoria;
import com.metabit.ventasenlinea.entity.Subcategoria;
import com.metabit.ventasenlinea.repository.SubcategoriaJpaRepository;
import com.metabit.ventasenlinea.service.SubcategoriaService;

@Service("subcategoriaServiceImpl")
public class SubcategoriaServiceImpl implements SubcategoriaService{

	//importamos repositorio
	@Autowired
	@Qualifier("subcategoriaJpaRepository")
	private SubcategoriaJpaRepository subcategoriaJpaRepository;
	
	@Override
	public List<Subcategoria> listAllSubcategorias() {
		return subcategoriaJpaRepository.findAll();
	}

	@Override
	public List<Subcategoria> listAllSubcategoriasByCategoria(Categoria categoria) {
		return subcategoriaJpaRepository.findAllByCategoria(categoria);
	}

	@Override
	public Subcategoria createSubcategoria(Subcategoria subcategoria) {
		return subcategoriaJpaRepository.save(subcategoria);
	}

	@Override
	public Subcategoria updateSubcategoria(Subcategoria subcategoria) {
		return subcategoriaJpaRepository.save(subcategoria);
	}

	@Override
	public Subcategoria getSubcategoria(int id) {
		return subcategoriaJpaRepository.findById(id).get();
	}

}
