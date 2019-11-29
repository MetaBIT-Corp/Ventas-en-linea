package com.metabit.ventasenlinea.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.ventasenlinea.entity.Cliente;
import com.metabit.ventasenlinea.entity.Empleado;
import com.metabit.ventasenlinea.entity.User;
@Repository("empleadoJpaRepository")
public interface EmpleadoJpaRepository extends JpaRepository<Empleado, Serializable>{
	public abstract Empleado findByIdEmpleado(int id);
	public abstract Empleado findByUser(User user);
}
