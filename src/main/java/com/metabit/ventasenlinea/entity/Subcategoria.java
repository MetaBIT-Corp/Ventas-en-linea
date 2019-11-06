package com.metabit.ventasenlinea.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "subcategorias")
public class Subcategoria {
	
	@Id
	@GeneratedValue
	@Column(name = "id_subcategoria")
	private int id_subcategoria;
	
	@Column(name = "nombre_subcategoria")
	private String nombre_subcategoria;
	
	@Column(name = "descripcion_subcategoria")
	private String descripcion_subcategoria;
	
	//foranea
	@ManyToOne
	private Categoria categoria;

	public Subcategoria() {
		super();
	}

	public Subcategoria(int id_subcategoria, String nombre_subcategoria, String descripcion_subcategoria,
			Categoria categoria) {
		super();
		this.id_subcategoria = id_subcategoria;
		this.nombre_subcategoria = nombre_subcategoria;
		this.descripcion_subcategoria = descripcion_subcategoria;
		this.categoria = categoria;
	}

	public int getId_subcategoria() {
		return id_subcategoria;
	}

	public void setId_subcategoria(int id_subcategoria) {
		this.id_subcategoria = id_subcategoria;
	}

	public String getNombre_subcategoria() {
		return nombre_subcategoria;
	}

	public void setNombre_subcategoria(String nombre_subcategoria) {
		this.nombre_subcategoria = nombre_subcategoria;
	}

	public String getDescripcion_subcategoria() {
		return descripcion_subcategoria;
	}

	public void setDescripcion_subcategoria(String descripcion_subcategoria) {
		this.descripcion_subcategoria = descripcion_subcategoria;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
	
	
}
