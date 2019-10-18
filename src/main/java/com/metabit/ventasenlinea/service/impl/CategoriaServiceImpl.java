package com.metabit.ventasenlinea.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.metabit.ventasenlinea.entity.Categoria;
import com.metabit.ventasenlinea.entity.Departamento;
import com.metabit.ventasenlinea.repository.CategoriaJpaRepository;
import com.metabit.ventasenlinea.service.CategoriaService;

@Service("categoriaServiceImpl")
public class CategoriaServiceImpl implements CategoriaService{

	@Autowired
	@Qualifier("categoriaJpaRepository")
	private CategoriaJpaRepository categoriaJpaRepository;
	
	@Override
	public List<Categoria> getCategorias(Departamento departamento) {
		return categoriaJpaRepository.findByDepartamento(departamento);
	}

}
