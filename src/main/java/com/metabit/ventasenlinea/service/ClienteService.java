package com.metabit.ventasenlinea.service;



import com.metabit.ventasenlinea.entity.Cliente;
import com.metabit.ventasenlinea.entity.User;

public interface ClienteService {
	public abstract void createCliente(Cliente cliente);
	public abstract Cliente BuscarUsuario(User user);
}
