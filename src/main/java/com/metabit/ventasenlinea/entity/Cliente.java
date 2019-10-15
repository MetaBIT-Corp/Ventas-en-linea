package com.metabit.ventasenlinea.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {

	@Id
	@GeneratedValue
	@Column(name = "id_ciente", nullable = false)
	private int id_cliente;

	@Column(name = "nombre_cliente", nullable = false)
	private String nombre_cliente;

	@Column(name = "apellido_cliente", nullable = false)
	private String apellido_cliente;

	@Column(name = "direccion", nullable = false)
	private String direccion;

	@OneToOne()
	private User user;

	public Cliente() {
	}

	public Cliente(int id_cliente, String nombre_cliente, String apellido_cliente, String direccion, User user) {
		super();
		this.id_cliente = id_cliente;
		this.nombre_cliente = nombre_cliente;
		this.apellido_cliente = apellido_cliente;
		this.direccion = direccion;
		this.user = user;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getNombre_cliente() {
		return nombre_cliente;
	}

	public void setNombre_cliente(String nombre_cliente) {
		this.nombre_cliente = nombre_cliente;
	}

	public String getApellido_cliente() {
		return apellido_cliente;
	}

	public void setApellido_cliente(String apellido_cliente) {
		this.apellido_cliente = apellido_cliente;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}

