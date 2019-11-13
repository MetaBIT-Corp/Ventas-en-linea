package com.metabit.ventasenlinea.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.ventasenlinea.entity.Departamento;
import com.metabit.ventasenlinea.repository.DepartamentoJpaRepository;
import com.metabit.ventasenlinea.service.DepartamentoService;

@Service("departamentoServiceImpl")
public class DepartamentoServiceImpl implements DepartamentoService {

	@Autowired
	@Qualifier("departamentoJpaRepository")
	private DepartamentoJpaRepository departamentoJpaRepository;

	@Override
	public List<Departamento> getDepartamentos() {
		return departamentoJpaRepository.findAll();
	}

	@Override
	public Departamento getDepartamento(int id) {
		return departamentoJpaRepository.findById(id).get();
	}

	@Override
	public Departamento createDepartamento(Departamento departamento) {
		return departamentoJpaRepository.save(departamento);
	}

}
