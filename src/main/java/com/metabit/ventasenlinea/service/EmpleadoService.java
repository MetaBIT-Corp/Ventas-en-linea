package com.metabit.ventasenlinea.service;
import java.util.List;

import com.metabit.ventasenlinea.entity.Empleado;


public interface EmpleadoService {

	public void crearEmpleado(Empleado empleado);
	public List<Empleado> getEmpleados();
	
}
