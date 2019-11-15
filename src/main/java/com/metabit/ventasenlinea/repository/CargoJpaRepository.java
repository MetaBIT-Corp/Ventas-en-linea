package com.metabit.ventasenlinea.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.ventasenlinea.entity.Cargo;

@Repository("cargoJpaRepository")
public interface CargoJpaRepository extends JpaRepository<Cargo, Serializable>{
	public abstract Cargo findById(int id);
}
