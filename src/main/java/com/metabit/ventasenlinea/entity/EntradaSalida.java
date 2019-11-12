package com.metabit.ventasenlinea.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="entrada_salida")
public class EntradaSalida {
	
	@Id
	@GeneratedValue
	@Column(name="id_entrada_salida")
	private int idEntradaSalida;
	
	@ManyToOne
	private Kardex kardex;
	
	@Column(name="fecha_entrada_salida")
	private Date fecha;
	
	@Column(name="is_entrada_salida")
	private boolean isEntradaSalida;
	
	@Column(name="cantidad")
	private int cantidad;
	
	@Column(name="precio")
	private double precio;
	
	
	public int getIdEntradaSalida() {
		return idEntradaSalida;
	}
	public void setIdEntradaSalida(int idEntradaSalida) {
		this.idEntradaSalida = idEntradaSalida;
	}
	public Kardex getKardex() {
		return kardex;
	}
	public void setKardex(Kardex kardex) {
		this.kardex = kardex;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public boolean getIsEntradaSalida() {
		return isEntradaSalida;
	}
	public void setIsEntradaSalida(boolean isEntradaSalida) {
		this.isEntradaSalida = isEntradaSalida;
	}
	public void setEntradaSalida(boolean isEntradaSalida) {
		this.isEntradaSalida = isEntradaSalida;
	}
}
