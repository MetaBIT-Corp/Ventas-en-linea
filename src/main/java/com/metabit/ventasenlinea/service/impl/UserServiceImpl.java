package com.metabit.ventasenlinea.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.ventasenlinea.entity.Cliente;
import com.metabit.ventasenlinea.entity.User;
import com.metabit.ventasenlinea.repository.ClienteJpaRepository;
import com.metabit.ventasenlinea.repository.UserJpaRepository;
import com.metabit.ventasenlinea.service.UserService;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{
	

	@Autowired
	@Qualifier("userJpaRepository")
	private UserJpaRepository userJpaRepository;
	
	@Autowired
	@Qualifier("clienteJpaRepository")
	private ClienteJpaRepository clienteJpaRepository;
	
	@Override
	public void createUser(User user) {
		userJpaRepository.save(user); 
	}

	@Override
	public User findById(int id_user) {
		return userJpaRepository.findByIdUser(id_user);
	}

	@Override
	public User findByEmail(String email) {
		return userJpaRepository.findByEmail(email);
	}

	@Override
	public List<Cliente> findAllClientes() {
		return clienteJpaRepository.findAll();
	}
	
	
}
