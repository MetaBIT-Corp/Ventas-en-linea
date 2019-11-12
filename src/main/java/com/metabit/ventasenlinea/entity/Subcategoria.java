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
	private int idSubcategoria;

	@Column(name = "nombre_subcategoria")
	private String nombreSubcategoria;

	@Column(name = "descripcion_subcategoria")
	private String descripcionSubcategoria;

	@Column(name = "habilitado")
	private boolean habilitado;

	// foranea
	@ManyToOne
	private Categoria categoria;

	public Subcategoria() {
		super();
	}

	public Subcategoria(int idSubcategoria, String nombreSubcategoria, String descripcionSubcategoria,
			boolean habilitado, Categoria categoria) {
		super();
		this.idSubcategoria = idSubcategoria;
		this.nombreSubcategoria = nombreSubcategoria;
		this.descripcionSubcategoria = descripcionSubcategoria;
		this.habilitado = habilitado;
		this.categoria = categoria;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public int getIdSubcategoria() {
		return idSubcategoria;
	}

	public void setIdSubcategoria(int idSubcategoria) {
		this.idSubcategoria = idSubcategoria;
	}

	public String getNombreSubcategoria() {
		return nombreSubcategoria;
	}

	public void setNombreSubcategoria(String nombreSubcategoria) {
		this.nombreSubcategoria = nombreSubcategoria;
	}

	public String getDescripcionSubcategoria() {
		return descripcionSubcategoria;
	}

	public void setDescripcionSubcategoria(String descripcionSubcategoria) {
		this.descripcionSubcategoria = descripcionSubcategoria;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
