package com.metabit.ventasenlinea.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.ventasenlinea.entity.Kardex;
import com.metabit.ventasenlinea.entity.Producto;

@Repository("kardexJpaRepository")
public interface KardexJpaRepository extends JpaRepository<Kardex, Serializable>{
	
	public Kardex findByIdKardex(int id_kardex);
	
	public Kardex findByProducto(Producto producto);

}
