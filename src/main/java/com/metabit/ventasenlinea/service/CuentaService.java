package com.metabit.ventasenlinea.service;

import com.metabit.ventasenlinea.entity.Cliente;
import com.metabit.ventasenlinea.entity.Cuenta;
import com.metabit.ventasenlinea.entity.User;

public interface CuentaService {
	
	public abstract void createCuenta(Cuenta cuenta);
	
	public abstract Cuenta getCuenta(Cliente cliente);

}
