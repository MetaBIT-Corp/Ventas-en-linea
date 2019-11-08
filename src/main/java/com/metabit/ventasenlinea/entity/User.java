package com.metabit.ventasenlinea.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

//import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue
	@Column(name = "id_user", unique = true, nullable = false)
	private int idUser;

	@NotEmpty(message = "Ingrese su correo")
	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@NotEmpty(message = "Ingrese una contraseña")
	@Column(name = "password", nullable = false)
	private String password;

	@NotEmpty(message = "Confirmar contraseña")
	@Transient
	private String passwordConfirm;

	@Column(name = "codigo_verificacion", nullable = false)
	private String codigoVerificacion;

	//indica si el usuario está disponible
	@Column(name = "verifyed", nullable = false)
	private int verifyed;

	/*@Column(name = "role", nullable = false)
	private int role;*/
	
	//Indica los roles que tiene el usuario (no se crea en la base de datos)
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<UserRole> userRole = new HashSet<UserRole>();

	public User() {
	}

	public User(int idUser, String email, String password, String passwordConfirm, String codigoVerificacion,
			int verifyed, int role) {
		super();
		this.idUser = idUser;
		this.email = email;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.codigoVerificacion = codigoVerificacion;
		this.verifyed = verifyed;
		//this.role = role; //0: admin; 1: encargado bodega; 2: encargado ventas; 3: cliente
	}
	
	//Constructor con role
	public User(int idUser, String email, String password, String passwordConfirm, String codigoVerificacion,
			int verifyed, int role, Set<UserRole> userRole) {
		super();
		this.idUser = idUser;
		this.email = email;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.codigoVerificacion = codigoVerificacion;
		this.verifyed = verifyed;
		//this.role = role;
		this.userRole = userRole;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getCodigoVerificacion() {
		return codigoVerificacion;
	}

	public void setCodigoVerificacion(String codigoVerificacion) {
		this.codigoVerificacion = codigoVerificacion;
	}

	public int getVerifyed() {
		return verifyed;
	}

	public void setVerifyed(int verifyed) {
		this.verifyed = verifyed;
	}
/*
	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}*/

	public Set<UserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}
	
	

}
