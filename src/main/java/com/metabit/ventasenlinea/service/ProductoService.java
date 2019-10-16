package com.metabit.ventasenlinea.service;

import java.util.List;

import com.metabit.ventasenlinea.entity.Producto;

public interface ProductoService {
	/**
	 * Metodo encargado de retornar todos los productos sin importar categorizacion.
	 * @return Listado de productos
	 * @author Ricardo Estupinian
	 */
	public abstract List<Producto> getProductos();
	
	public abstract void addProduct(Producto producto);
	
	public abstract void deleteAll();
}
