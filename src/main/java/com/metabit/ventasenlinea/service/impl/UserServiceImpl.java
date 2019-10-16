package com.metabit.ventasenlinea.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.ventasenlinea.entity.User;
import com.metabit.ventasenlinea.repository.UserJpaRepository;
import com.metabit.ventasenlinea.service.UserService;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{
	

	@Autowired
	@Qualifier("userJpaRepository")
	private UserJpaRepository userJpaRepository;
	
	@Override
	public void createUser(User user) {
		userJpaRepository.save(user); 
	}

	@Override
	public User findById(int id_user) {
		return userJpaRepository.findByIdUser(id_user);
	}
}
