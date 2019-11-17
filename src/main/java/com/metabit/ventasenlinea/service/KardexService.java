package com.metabit.ventasenlinea.service;

import java.util.List;

import com.metabit.ventasenlinea.entity.Kardex;
import com.metabit.ventasenlinea.entity.Producto;

public interface KardexService {
	public List<Kardex> getAllKardex();
	
	public Kardex getKardex(int id);
	
	public void addKardex(Kardex kardex);
	
	public abstract Kardex getKardexByProducto(Producto producto);
}
