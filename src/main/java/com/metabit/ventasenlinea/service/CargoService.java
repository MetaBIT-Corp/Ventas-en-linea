package com.metabit.ventasenlinea.service;

import java.util.List;

import com.metabit.ventasenlinea.entity.Cargo;

public interface CargoService {
	public List<Cargo> getCargos();
	public Cargo buscarPorId(int id);
}
