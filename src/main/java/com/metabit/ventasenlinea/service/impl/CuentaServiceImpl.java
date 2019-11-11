package com.metabit.ventasenlinea.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.ventasenlinea.entity.Cuenta;
import com.metabit.ventasenlinea.repository.CuentaJpaRepository;
import com.metabit.ventasenlinea.service.CuentaService;

@Service("cuentaServiceImpl")
public class CuentaServiceImpl implements CuentaService{

	@Autowired
	@Qualifier("cuentaJpaRepository")
	private CuentaJpaRepository cuentaJpaRepository;

	@Override
	public void createCuenta(Cuenta cuenta) {
		cuentaJpaRepository.save(cuenta);
	}
}
