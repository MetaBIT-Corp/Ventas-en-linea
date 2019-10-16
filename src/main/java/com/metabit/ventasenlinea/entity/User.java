package com.metabit.ventasenlinea.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue
	@Column(name = "id_user", nullable = false)
	private int idUser;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Transient
	private String passwordConfirm;

	@Column(name = "codigo_verificacion", nullable = false)
	private String codigoVerificacion;

	@Column(name = "verifyed", nullable = false)
	private int verifyed;

	@Column(name = "role", nullable = false)
	private int role;

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
		this.role = role; //0: admin; 1: encargado bodega; 2: encargado ventas; 3: cliente
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

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

}
