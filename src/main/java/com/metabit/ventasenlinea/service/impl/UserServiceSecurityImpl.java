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

import com.metabit.ventasenlinea.repository.UserJpaRepository;

@Service("userServiceSecurityImpl")
public class UserServiceSecurityImpl implements UserDetailsService {
	@Autowired
	@Qualifier("userJpaRepository")
	private UserJpaRepository userJpaRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.metabit.ventasenlinea.entity.User user = userJpaRepository.findByEmail(username);
		List<GrantedAuthority> authorities = buildAuthorities(user.getRole());
		return buildUser(user, authorities);
	}
	
	private User buildUser(com.metabit.ventasenlinea.entity.User user, List<GrantedAuthority> authorities) {
		if(user.getVerifyed()==1) {
			return new User(user.getEmail(), user.getPassword(), true, true, true, true, authorities);
		}else {
			return new User(user.getEmail(), user.getPassword(), false, true, true, true, authorities);
		}
		
	}
	
	private List<GrantedAuthority> buildAuthorities(int role){
		Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();
		auths.add(new SimpleGrantedAuthority(String.valueOf(role)));
		return new ArrayList<GrantedAuthority>(auths);
	}
	
	

}
