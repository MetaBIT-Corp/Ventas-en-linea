package com.metabit.ventasenlinea.service;
import java.util.List;

import com.metabit.ventasenlinea.entity.Empleado;
import com.metabit.ventasenlinea.entity.User;


public interface EmpleadoService {

	public void crearEmpleado(Empleado empleado);
	public List<Empleado> getEmpleados();
	public Empleado buscarPorID(int id);
	public Empleado buscarPorUser(User user);
}
