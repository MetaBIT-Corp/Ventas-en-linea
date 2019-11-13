package com.metabit.ventasenlinea.service;

import java.util.List;
import com.metabit.ventasenlinea.entity.Categoria;
import com.metabit.ventasenlinea.entity.Departamento;

public interface CategoriaService {

	public abstract List<Categoria> getCategorias(Departamento departamento);
	
	public abstract Categoria getCategoria(int idCategoria);
	
	public abstract Categoria createCategoria(Categoria categoria);
	
	public abstract Categoria updateCategoria(Categoria categoria);
}
