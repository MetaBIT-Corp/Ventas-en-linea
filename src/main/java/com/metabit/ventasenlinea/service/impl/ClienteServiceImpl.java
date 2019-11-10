package com.metabit.ventasenlinea.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.ventasenlinea.entity.Cliente;
import com.metabit.ventasenlinea.entity.User;
import com.metabit.ventasenlinea.repository.ClienteJpaRepository;
import com.metabit.ventasenlinea.service.ClienteService;

@Service("clienteServiceImpl")
public class ClienteServiceImpl implements ClienteService{

	@Autowired
	@Qualifier("clienteJpaRepository")
	private ClienteJpaRepository clienteJpaRepository;
	
	@Override
	public void createCliente(Cliente cliente) {
		clienteJpaRepository.save(cliente);
	}
	@Override
	public Cliente BuscarUsuario(User user) {
		return clienteJpaRepository.findByUser(user);
	}
}
