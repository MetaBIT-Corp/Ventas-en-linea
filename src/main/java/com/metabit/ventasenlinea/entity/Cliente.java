package com.metabit.ventasenlinea.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
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
	@Column(name = "id_cliente", nullable = false)
	private int idCliente;

	@NotEmpty(message = "Ingrese su nombre")
	@Column(name = "nombre_cliente", nullable = false)
	private String nombreCliente;

	@NotEmpty(message = "Ingrese su apellido")
	@Column(name = "apellido_cliente", nullable = false)
	private String apellidoCliente;

	@NotEmpty(message = "Ingrese su dirección")
	@Column(name = "direccion", nullable = false)
	private String direccion;

	@OneToOne()
	private User user;

	public Cliente() {
	}

	public Cliente(int idCliente, String nombreCliente, String apellidoCliente, String direccion, User user) {
		super();
		this.idCliente = idCliente;
		this.nombreCliente = nombreCliente;
		this.apellidoCliente = apellidoCliente;
		this.direccion = direccion;
		this.user = user;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getApellidoCliente() {
		return apellidoCliente;
	}

	public void setApellidoCliente(String apellidoCliente) {
		this.apellidoCliente = apellidoCliente;
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

	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", nombreCliente=" + nombreCliente + ", apellidoCliente="
				+ apellidoCliente + ", direccion=" + direccion + ", user=" + user + "]";
	}
	

}
