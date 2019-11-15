package com.metabit.ventasenlinea.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="empleados")
public class Empleado {
	@Id
	@GeneratedValue
	@Column(name = "id_empleado", nullable = false)
	private int idEmpleado;

	@NotEmpty(message = "Ingrese su nombre")
	@Column(name = "nombre_empleado", nullable = false)
	private String nombreEmpleado;

	@NotEmpty(message = "Ingrese su apellido")
	@Column(name = "apellido_empleado", nullable = false)
	private String apellidoEmpleado;	
	
	@NotEmpty(message = "Ingrese su dirección")
	@Column(name = "direccion", nullable = false)	
	private String direccion;
	
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@OneToOne()
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "id_cargo", nullable = false)
	private Cargo cargo;

	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getNombreEmpleado() {
		return nombreEmpleado;
	}

	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}

	public String getApellidoEmpleado() {
		return apellidoEmpleado;
	}

	public void setApellidoEmpleado(String apellidoEmpleado) {
		this.apellidoEmpleado = apellidoEmpleado;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	
	
	public Empleado(int idEmpleado, @NotEmpty(message = "Ingrese su nombre") String nombreEmpleado,
			@NotEmpty(message = "Ingrese su apellido") String apellidoEmpleado,
			@NotEmpty(message = "Ingrese su dirección") String direccion, User user, Cargo cargo) {
		super();
		this.idEmpleado = idEmpleado;
		this.nombreEmpleado = nombreEmpleado;
		this.apellidoEmpleado = apellidoEmpleado;
		this.direccion = direccion;
		this.user = user;
		this.cargo = cargo;
	}

	public Empleado() {
		
	}

	@Override
	public String toString() {
		return "Empleado [idEmpleado=" + idEmpleado + ", nombreEmpleado=" + nombreEmpleado + ", apellidoEmpleado="
				+ apellidoEmpleado + ", direccion=" + direccion + ", user=" + user + ", cargo=" + cargo + "]";
	}
	

	
}
