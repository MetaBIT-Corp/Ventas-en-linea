package com.metabit.ventasenlinea.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.ventasenlinea.entity.ArticuloPedido;
import com.metabit.ventasenlinea.repository.ArticuloPedidoJpaRepository;
import com.metabit.ventasenlinea.service.ArticuloPedidoService;

@Service("articuloPedidoServiceImpl")
public class ArticuloPedidoServiceImpl implements ArticuloPedidoService{
	
	@Autowired
	@Qualifier("articuloPedidoJpaRepository")
	private ArticuloPedidoJpaRepository articuloPedidoJpaRepository;

	@Override
	public ArticuloPedido createArticuloPedido(ArticuloPedido ap) {
		return articuloPedidoJpaRepository.save(ap);
	}

}
