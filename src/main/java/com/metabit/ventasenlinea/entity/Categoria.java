package com.metabit.ventasenlinea.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "categorias")
public class Categoria {

	@Id
	@GeneratedValue
	@Column(name = "id_categoria", nullable = false)
	private int id;

	@NotNull
	@Size(min = 2, max = 255)
	@Column(name = "nombre_categoria", nullable = false)
	private String nombre;

	public List<Subcategoria> getSubcategorias() {
		return subcategorias;
	}

	public void setSubcategorias(List<Subcategoria> subcategorias) {
		this.subcategorias = subcategorias;
	}

	@NotNull
	@Size(min = 2, max = 255)
	@Column(name = "descripcion_categoria", nullable = false)
	private String descripcion;

	@Column(name = "habilitado")
	private boolean habilitado;

	@ManyToOne
	@JoinColumn(name = "id_departamento", nullable = false)
	private Departamento departamento;
	
	@OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
	@JsonIgnore
    private List<Subcategoria> subcategorias =  new ArrayList<>();
	
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

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Categoria(int id, String nombre, String descripcion, boolean habilitado, Departamento departamento) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.habilitado = habilitado;
		this.departamento = departamento;
	}

	public Categoria() {
		super();
	}

}
