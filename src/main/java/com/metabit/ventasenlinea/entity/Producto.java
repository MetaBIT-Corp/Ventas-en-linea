package com.metabit.ventasenlinea.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "productos")
public class Producto {

	// Falta el campo para la llave foranea de subgategoria

	@Id
	@GeneratedValue
	@Column(name = "id_articulo")
	private int idArticulo;

	@Column(name = "imagen")
	private String imagen;

	@NotEmpty(message = "Ingrese la marca del producto")
	@Column(name = "marca", length = 50)
	private String marca;

	@NotEmpty(message = "Ingrese el título del producto")
	@Column(name = "titulo", length = 50)
	private String titulo;

	@Min(value = 0, message = "El margen de ganancia no puede ser negativo")
	@Column(name = "margen_ganancia")
	private double margenGanancia;

	@Min(value = 0, message = "El descuento no puede ser negativo")
	@Column(name = "porcentaje_descuento")
	private double porcentajeDescuento;

	@NotEmpty(message = "Ingrese la descripción del producto")
	@Column(name = "descripcion_articulo")
	private String descripcionArticulo;

	@Column(name = "habilitado")
	private int habilitado;

	@ManyToOne
	@JoinColumn(name = "id_subcategoria")
	private Subcategoria subcategoria;
	
	@OneToOne(mappedBy = "producto", fetch = FetchType.LAZY)
	@JsonIgnore
    private Kardex kardex;

	public Producto() {
	}

	public Producto(int idArticulo, String imagen, String marca, String titulo, double margenGanancia,
			double porcentajeDescuento, String descripcionArticulo, int habilitado, Subcategoria subcategoria) {
		this.idArticulo = idArticulo;
		this.imagen = imagen;
		this.marca = marca;
		this.titulo = titulo;
		this.margenGanancia = margenGanancia;
		this.porcentajeDescuento = porcentajeDescuento;
		this.descripcionArticulo = descripcionArticulo;
		this.habilitado = habilitado;
		this.subcategoria = subcategoria;
	}

	public int getIdArticulo() {
		return idArticulo;
	}

	public void setIdArticulo(int idArticulo) {
		this.idArticulo = idArticulo;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public double getMargenGanancia() {
		return margenGanancia;
	}

	public void setMargenGanancia(double margenGanancia) {
		this.margenGanancia = margenGanancia;
	}

	public double getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	public void setPorcentajeDescuento(double porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}

	public String getDescripcionArticulo() {
		return descripcionArticulo;
	}

	public void setDescripcionArticulo(String descripcionArticulo) {
		this.descripcionArticulo = descripcionArticulo;
	}

	public int getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(int habilitado) {
		this.habilitado = habilitado;
	}

	public Subcategoria getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(Subcategoria subcategoria) {
		this.subcategoria = subcategoria;
	}

	public Kardex getKardex() {
		return kardex;
	}

	public void setKardex(Kardex kardex) {
		this.kardex = kardex;
	}

	@Override
	public String toString() {
		return "Producto [idArticulo=" + idArticulo + ", imagen=" + imagen + ", marca=" + marca + ", titulo=" + titulo
				+ ", margenGanancia=" + margenGanancia + ", porcentajeDescuento=" + porcentajeDescuento
				+ ", descripcionArticulo=" + descripcionArticulo + ", habilitado=" + habilitado + ", subcategoria="
				+ subcategoria + "]";
	}

}
