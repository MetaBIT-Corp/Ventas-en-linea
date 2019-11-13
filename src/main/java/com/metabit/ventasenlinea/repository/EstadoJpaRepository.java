package com.metabit.ventasenlinea.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.ventasenlinea.entity.Estado;

@Repository("estadoJpaRepository")
public interface EstadoJpaRepository extends JpaRepository<Estado, Serializable>{
	
	public Estado findByIdEstado(int id);
}
