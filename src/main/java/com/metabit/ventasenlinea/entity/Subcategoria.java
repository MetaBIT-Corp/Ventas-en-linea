package com.metabit.ventasenlinea.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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

import org.hibernate.annotations.IndexColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "subcategorias")
public class Subcategoria {

	@Id
	@GeneratedValue
	@Column(name = "id_subcategoria")
	private int idSubcategoria;

	@NotNull
	@Size(min = 2, max = 255)
	@Column(name = "nombre_subcategoria")
	private String nombreSubcategoria;

	@NotNull
	@Size(min = 2, max = 255)
	@Column(name = "descripcion_subcategoria")
	private String descripcionSubcategoria;

	@Column(name = "habilitado")
	private boolean habilitado;

	// foranea
	@ManyToOne
	private Categoria categoria;

	@OneToMany(mappedBy = "subcategoria", fetch = FetchType.LAZY)
	@JsonIgnore
    private List<Producto> productos =  new ArrayList<>();;
	 
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

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
}
