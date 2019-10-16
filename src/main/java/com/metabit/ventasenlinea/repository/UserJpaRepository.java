package com.metabit.ventasenlinea.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.ventasenlinea.entity.User;

@Repository("userJpaRepository")
public interface UserJpaRepository extends JpaRepository<User, Serializable>{
	public abstract User findByIdUser(int id_user);
	
	public abstract User findByEmail(String email);
}
