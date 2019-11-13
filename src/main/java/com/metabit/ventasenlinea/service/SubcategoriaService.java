package com.metabit.ventasenlinea.service;

import java.util.List;

import com.metabit.ventasenlinea.entity.Categoria;
import com.metabit.ventasenlinea.entity.Subcategoria;

public interface SubcategoriaService {
	
	public abstract List<Subcategoria> listAllSubcategorias();
	
	public abstract List<Subcategoria> listAllSubcategoriasByCategoria(Categoria categoria);
	
	public abstract Subcategoria createSubcategoria (Subcategoria subcategoria);
}
