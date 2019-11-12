package com.metabit.ventasenlinea.service;

import java.util.List;

import com.metabit.ventasenlinea.entity.Cliente;
import com.metabit.ventasenlinea.entity.User;

public interface UserService {

	public abstract void createUser(User user);
	public abstract User findById(int id_user);
	public abstract User findByEmail(String email);
	public abstract List<Cliente> findAllClientes();
	//Agregados por Diego
	void updatePassword(String password, int userId);
	public User updateUser(User user);
	
}
