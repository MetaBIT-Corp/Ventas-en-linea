package com.metabit.ventasenlinea.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.ventasenlinea.entity.Empleado;
import com.metabit.ventasenlinea.repository.EmpleadoJpaRepository;
import com.metabit.ventasenlinea.service.EmpleadoService;

@Service("empleadoServiceImpl")
public class EmpleadoServiceImpl implements EmpleadoService{
	@Autowired
	@Qualifier("empleadoJpaRepository")
	private EmpleadoJpaRepository empleadoJpaRepository;
	
	@Override
	public void crearEmpleado(Empleado empleado) {
		empleadoJpaRepository.save(empleado);
	}

	@Override
	public List<Empleado> getEmpleados() {
		
		return empleadoJpaRepository.findAll();
	}

	@Override
	public Empleado buscarPorID(int id) {
		
		return empleadoJpaRepository.findByIdEmpleado(id);
	}
}
