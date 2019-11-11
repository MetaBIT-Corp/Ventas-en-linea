package com.metabit.ventasenlinea.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.ventasenlinea.entity.Kardex;
import com.metabit.ventasenlinea.repository.KardexJpaRepository;
import com.metabit.ventasenlinea.service.KardexService;

@Service("kardexServiceImpl")
public class KardexServiceImpl implements KardexService{
	
	@Autowired
	@Qualifier("kardexJpaRepository")
	private KardexJpaRepository kardexRepository;
	
	@Override
	public List<Kardex> getAllKardex() {
		return kardexRepository.findAll();
	}

	@Override
	public Kardex getKardex(int id) {
		return kardexRepository.findByIdKardex(id);
	}
	
	
}
