package com.metabit.ventasenlinea.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.metabit.ventasenlinea.entity.UserRole;
import com.metabit.ventasenlinea.repository.UserJpaRepository;

@Service("userServiceSecurityImpl")
public class UserServiceSecurityImpl implements UserDetailsService {
	@Autowired
	@Qualifier("userJpaRepository")
	private UserJpaRepository userJpaRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//recuperamos el entity user por email
		com.metabit.ventasenlinea.entity.User user = userJpaRepository.findByEmail(email);
		//convertimos los roles en authorities mediante la función buildAuthorities
		List<GrantedAuthority> authorities = buildAuthorities(user.getUserRole());
		//convertimos el user entity en un User de Spring Security, y lo retornamos
		return buildUser(user, authorities);
	}
	
	//Convierte el user entity en un User details que spring security necesita
	private User buildUser(com.metabit.ventasenlinea.entity.User user, List<GrantedAuthority> authorities) {
		
		//si el usuario no ha sido verificado(verifyed=0) no estará habilitado
		if(user.getVerifyed()==1) {
			return new User(user.getEmail(), user.getPassword(), true, true, true, true, authorities);
		}else {
			return new User(user.getEmail(), user.getPassword(), false, true, true, true, authorities);
		}
		
	}
	
	//Convierte nuestros roles a una lista de objetos GrantedAuthority
	private List<GrantedAuthority> buildAuthorities(Set<UserRole> userRoles){
		Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();
		for(UserRole userRole : userRoles) {
			auths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
		return new ArrayList<GrantedAuthority>(auths);
	}
	
	

}
