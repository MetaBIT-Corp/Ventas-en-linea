package com.metabit.ventasenlinea.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.ventasenlinea.entity.Cliente;
import com.metabit.ventasenlinea.entity.Cuenta;

@Repository("cuentaJpaRepository")
public interface CuentaJpaRepository extends JpaRepository<Cuenta, Serializable> {
	public abstract Cuenta findByCliente(Cliente cliente);
}
