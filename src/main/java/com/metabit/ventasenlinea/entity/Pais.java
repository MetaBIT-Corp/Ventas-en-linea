package com.metabit.ventasenlinea.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Paises")
public class Pais {
	
	@Id
	@GeneratedValue
	@Column(name = "id_pais", nullable = false)
	private int id_pais;
	
	@Column(name = "nombre_pais", nullable = false)
	private String nombre_pais;
	
	@Column(name = "costo_envio", nullable = false)
	private float costo_envio;
	
	@Column(name = "impuesto", nullable = false)
	private float impuesto;

	public Pais() {
		super();
	}

	public Pais(int id_pais, String nombre_pais, float costo_envio, float impuesto) {
		super();
		this.id_pais = id_pais;
		this.nombre_pais = nombre_pais;
		this.costo_envio = costo_envio;
		this.impuesto = impuesto;
	}

	public int getId_pais() {
		return id_pais;
	}

	public void setId_pais(int id_pais) {
		this.id_pais = id_pais;
	}

	public String getNombre_pais() {
		return nombre_pais;
	}

	public void setNombre_pais(String nombre_pais) {
		this.nombre_pais = nombre_pais;
	}

	public float getCosto_envio() {
		return costo_envio;
	}

	public void setCosto_envio(float costo_envio) {
		this.costo_envio = costo_envio;
	}

	public float getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(float impuesto) {
		this.impuesto = impuesto;
	}
	
}
