package com.metabit.ventasenlinea.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "departamentos")
public class Departamento {

	@Id
	@GeneratedValue
	@Column(name = "id_departamento", nullable = false)
	private int id;

	@Column(name = "nombre_departamento", nullable = false)
	private String nombre;

	@Column(name = "descripcion_departamento", nullable = false)
	private String descripcion;
	
	@Column(name = "habilitado")
	
	private boolean habilitado;

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Departamento(int id, String nombre, String descripcion, boolean habilitado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.habilitado = habilitado;
	}

	public Departamento() {
		super();
	}

}
