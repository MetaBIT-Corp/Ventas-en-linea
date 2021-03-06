package com.metabit.ventasenlinea.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.ventasenlinea.entity.Pais;
import com.metabit.ventasenlinea.entity.Pedido;

@Repository("paisJpaRepository")
public interface PaisJpaRpository extends JpaRepository<Pais, Serializable> {
	public Pais findByIdPais(int id);
}
