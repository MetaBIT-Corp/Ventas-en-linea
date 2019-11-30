package com.metabit.ventasenlinea.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.metabit.ventasenlinea.entity.Producto;


@Repository("productoJpaRespository")
public interface ProductoJpaRepository extends JpaRepository<Producto, Serializable>{
	public abstract Producto findByIdArticulo(int id_articulo);
	public abstract List<Producto> findByTituloContaining(String name);
}
