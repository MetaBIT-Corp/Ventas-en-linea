package com.metabit.ventasenlinea.service;

import com.metabit.ventasenlinea.entity.User;

public interface UserService {

	public abstract void createUser(User user);
	public abstract User findById(int id_user);
}
