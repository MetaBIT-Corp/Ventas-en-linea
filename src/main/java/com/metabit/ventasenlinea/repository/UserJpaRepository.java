package com.metabit.ventasenlinea.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.metabit.ventasenlinea.entity.User;

@Repository("userJpaRepository")
public interface UserJpaRepository extends JpaRepository<User, Serializable>{
	public abstract User findByIdUser(int id_user);
	
	public abstract User findByEmail(String email);
	//Agregado por Diego
	@Modifying
	@Query("update User u set u.password = :password where u.id = :id")
	public void updatePassword(@Param("password") String password, @Param("id") int id);
}
