package com.metabit.ventasenlinea.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.ventasenlinea.entity.ArticuloPedido;

@Repository("articuloPedidoJpaRepository")
public interface ArticuloPedidoJpaRepository extends JpaRepository<ArticuloPedido, Serializable> {

}
