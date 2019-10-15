package com.metabit.ventasenlinea.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	@Id 
	@GeneratedValue
	@Column(name = "id_user", nullable = false)
	private int id_user;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "codigo_verificacion", nullable = false)
	private String codigo_verificacion;
	
	@Column(name = "verifyed", nullable = false)
	private int verifyed;
	
	@Column(name = "role", nullable = false)
	private int role;
	
	public User() {
	}

	public User(int id_user, String email, String password, String codigo_verificacion, int verifyed, int role) {
		super();
		this.id_user = id_user;
		this.email = email;
		this.password = password;
		this.codigo_verificacion = codigo_verificacion;
		this.verifyed = verifyed;
		this.role = role;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
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

	public String getCodigo_verificacion() {
		return codigo_verificacion;
	}

	public void setCodigo_verificacion(String codigo_verificacion) {
		this.codigo_verificacion = codigo_verificacion;
	}

	public int getVerifyed() {
		return verifyed;
	}

	public void setVerifyed(int verifyed) {
		this.verifyed = verifyed;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

}
