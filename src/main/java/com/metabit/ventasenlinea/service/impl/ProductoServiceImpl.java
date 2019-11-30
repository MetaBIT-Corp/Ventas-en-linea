package com.metabit.ventasenlinea.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.ventasenlinea.entity.Producto;
import com.metabit.ventasenlinea.entity.User;
import com.metabit.ventasenlinea.repository.ProductoJpaRepository;
import com.metabit.ventasenlinea.service.ProductoService;

@Service("productoServiceImpl")
public class ProductoServiceImpl implements ProductoService{
	
	@Autowired
	@Qualifier("productoJpaRespository")
	private ProductoJpaRepository productoRepository;
	
	@Override
	public List<Producto> getProductos() {
		return productoRepository.findAll();
	}

	@Override
	public void addProduct(Producto producto) {
		productoRepository.save(producto);
	}

	@Override
	public void deleteAll() {
		productoRepository.deleteAll();
	}
	
	 @Override
	public Producto findById(int id_articulo) {
		return productoRepository.findByIdArticulo(id_articulo);
	}

	@Override
	public Producto updateProducto(Producto producto) {
		return productoRepository.save(producto);
	}
	@Override
	public List<Producto> buscarProducto(String nombre){
		return productoRepository.findByTituloContaining(nombre);
	}
	 
}
