package com.metabit.ventasenlinea.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.ventasenlinea.entity.Pais;
import com.metabit.ventasenlinea.repository.PaisJpaRpository;
import com.metabit.ventasenlinea.service.PaisService;

@Service("paisServiceImpl")
public class PaisServiceImpl implements PaisService{
	
	@Autowired
	@Qualifier("paisJpaRepository")
	private PaisJpaRpository paisJpaRepository;

	@Override
	public Pais getPaisById(int id) {
		return paisJpaRepository.findByIdPais(id);
	}

	@Override
	public void deleteAllPais() {
		paisJpaRepository.deleteAll();
	}

	@Override
	public Pais createPais(Pais pais) {
		return paisJpaRepository.save(pais);
	}

	@Override
	public List<Pais> getAllPais() {
		return paisJpaRepository.findAll();
	}
	
}
