package com.metabit.ventasenlinea.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.ventasenlinea.entity.Cliente;
import com.metabit.ventasenlinea.entity.User;

@Repository("clienteJpaRepository")
public interface ClienteJpaRepository extends JpaRepository<Cliente, Serializable>{
	public Cliente findByUser(User user);
}
