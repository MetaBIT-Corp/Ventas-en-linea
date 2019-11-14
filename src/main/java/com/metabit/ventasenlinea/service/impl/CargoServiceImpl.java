package com.metabit.ventasenlinea.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.ventasenlinea.entity.Cargo;
import com.metabit.ventasenlinea.repository.CargoJpaRepository;
import com.metabit.ventasenlinea.service.CargoService;

@Service("cargoServiceImpl")
public class CargoServiceImpl implements CargoService{

	@Autowired
	@Qualifier("cargoJpaRepository")
	private CargoJpaRepository cargoJpaRepository;
	
	@Override
	public List<Cargo> getCargos() {		
		return cargoJpaRepository.findAll();
	}
	@Override
	public Cargo buscarPorId(int id) {		
		return cargoJpaRepository.findById(id);
	}

}
