package com.metabit.ventasenlinea.service;

import java.util.List;

import com.metabit.ventasenlinea.entity.Pais;

public interface PaisService {
	public abstract Pais getPaisById(int id);
	
	public abstract List<Pais> getAllPais();
	
	public abstract void deleteAllPais();
	
	public abstract Pais createPais(Pais pais);
}
