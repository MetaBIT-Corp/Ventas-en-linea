package com.metabit.ventasenlinea.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.ventasenlinea.entity.UserRole;
import com.metabit.ventasenlinea.repository.UserRoleJpaRepository;
import com.metabit.ventasenlinea.service.UserRoleService;

@Service("userRoleServiceImpl")
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	@Qualifier("userRoleJpaRepository")
	private UserRoleJpaRepository userRoleJpaRepository;
	
	@Override
	public void createUserRole(UserRole userRole) {
		userRoleJpaRepository.save(userRole);
	}
	

}
