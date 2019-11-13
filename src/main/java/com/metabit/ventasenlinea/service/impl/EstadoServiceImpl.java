package com.metabit.ventasenlinea.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.ventasenlinea.entity.Estado;
import com.metabit.ventasenlinea.repository.EstadoJpaRepository;
import com.metabit.ventasenlinea.service.EstadoService;

@Service("estadoServiceImpl")
public class EstadoServiceImpl implements EstadoService{
	
	@Autowired
	@Qualifier("estadoJpaRepository")
	private EstadoJpaRepository estadoJpaRepository;
	
	@Override
	public Estado getEstado(int id_estado) {
		return estadoJpaRepository.findByIdEstado(id_estado);
	}
	
	
}
