package com.metabit.ventasenlinea.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.ventasenlinea.entity.Cliente;
import com.metabit.ventasenlinea.entity.Estado;
import com.metabit.ventasenlinea.entity.Pedido;

@Repository("pedidoJpaRepository")
public interface PedidoJpaRepository extends JpaRepository<Pedido, Serializable> {
	
	public List<Pedido> findByCliente(Cliente id_cliente);
	
	public List<Pedido> findByEstado(Estado id_estado);
}
