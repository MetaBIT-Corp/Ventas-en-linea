package com.metabit.ventasenlinea.service;

import java.util.List;

import com.metabit.ventasenlinea.entity.Departamento;

public interface DepartamentoService {

	public abstract List<Departamento> getDepartamentos();
	public abstract Departamento getDepartamento(int id);
	
	public abstract Departamento createDepartamento(Departamento departamento);
}
