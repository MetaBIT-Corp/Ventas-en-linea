package com.metabit.ventasenlinea.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "departamentos")
public class Departamento {

	@Id
	@GeneratedValue
	@Column(name = "id_departamento", nullable = false)
	private int id;

	@NotNull
	@Size(min = 2, max = 255)
	@Column(name = "nombre_departamento", nullable = false)
	private String nombre;

	@NotNull
	@Size(min = 2, max = 255)
	@Column(name = "descripcion_departamento", nullable = false)
	private String descripcion;

	@Column(name = "habilitado")
	private boolean habilitado;
	
	@OneToMany(mappedBy = "departamento", fetch = FetchType.LAZY)
	@JsonIgnore
    private List<Categoria> categoria =  new ArrayList<>(); 
	
	public List<Categoria> getCategoria() {
		return categoria;
	}

	public void setCategoria(List<Categoria> categoria) {
		this.categoria = categoria;
	}

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

	@Override
	public String toString() {
		return "Departamento [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", habilitado="
				+ habilitado + "]";
	}

}
