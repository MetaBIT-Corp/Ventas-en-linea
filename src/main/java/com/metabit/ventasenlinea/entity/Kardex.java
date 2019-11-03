package com.metabit.ventasenlinea.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="kardex")
public class Kardex {
	
	@Id
	@GeneratedValue
	@Column(name="id_kardex")
	private int idKardex;
	
	@OneToOne
	private Producto producto;
	
	@Column(name="stock_minimo")
	private int stockMinimo;
	
	@Column(name="stock_maximo")
	private int stockMaximo;
	
	@Column(name="costo_unitario")
	private double costoUnitario;
	
	@Column(name="unidades_disponibles")
	private int unidadesDisponibles;

	public int getIdKardex() {
		return idKardex;
	}

	public void setIdKardex(int idKardex) {
		this.idKardex = idKardex;
	}


	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getStockMinimo() {
		return stockMinimo;
	}

	public void setStockMinimo(int stockMinimo) {
		this.stockMinimo = stockMinimo;
	}

	public int getStockMaximo() {
		return stockMaximo;
	}

	public void setStockMaximo(int stockMaximo) {
		this.stockMaximo = stockMaximo;
	}

	public double getCostoUnitario() {
		return costoUnitario;
	}

	public void setCostoUnitario(double costoUnitario) {
		this.costoUnitario = costoUnitario;
	}

	public int getUnidadesDisponibles() {
		return unidadesDisponibles;
	}

	public void setUnidadesDisponibles(int unidadesDisponibles) {
		this.unidadesDisponibles = unidadesDisponibles;
	}
}
