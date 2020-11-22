package com.metabit.ventasenlinea.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "paises")
public class Pais {

	@Id
	@GeneratedValue
	@Column(name = "id_pais", nullable = false)
	private int idPais;

	@Column(name = "nombre_pais", nullable = false)
	private String nombrePais;

	@Column(name = "costo_envio", nullable = false)
	private float costoEnvio;

	@Column(name = "impuesto", nullable = false)
	private float impuesto;

	public Pais() {
		super();
	}

	public Pais(int idPais, String nombrePais, float costoEnvio, float impuesto) {
		super();
		this.idPais = idPais;
		this.nombrePais = nombrePais;
		this.costoEnvio = costoEnvio;
		this.impuesto = impuesto;
	}

	public int getIdPais() {
		return idPais;
	}

	public void setIdPais(int idPais) {
		this.idPais = idPais;
	}

	public String getNombrePais() {
		return nombrePais;
	}

	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}

	public float getCostoEnvio() {
		return costoEnvio;
	}

	public void setCostoEnvio(float costoEnvio) {
		this.costoEnvio = costoEnvio;
	}

	public float getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(float impuesto) {
		this.impuesto = impuesto;
	}

}
