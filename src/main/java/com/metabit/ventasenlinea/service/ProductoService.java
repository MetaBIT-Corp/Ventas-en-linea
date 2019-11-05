package com.metabit.ventasenlinea.service;

import java.util.List;

import com.metabit.ventasenlinea.entity.Producto;
import com.metabit.ventasenlinea.entity.User;

public interface ProductoService {
	/**
	 * Metodo encargado de retornar todos los productos sin importar categorizacion.
	 * @return Listado de productos
	 * @author Ricardo Estupinian
	 */
	public abstract List<Producto> getProductos();
	
	public abstract void addProduct(Producto producto);
	
	public abstract void deleteAll();
	
	public abstract Producto findById(int id_producto);
	
	public abstract Producto updateProducto(Producto producto);
}
