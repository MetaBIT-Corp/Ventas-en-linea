package com.metabit.ventasenlinea.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "estados")
public class Estado {

	@Id
	@GeneratedValue
	@Column(name = "idEstado", nullable = false)
	private int idEstado;

	@Column(name = "titulo_estado", nullable = false)
	private String tituloEstado;

	public Estado() {
		super();
	}

	public int getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}

	public Estado(int id_estado, String tituloEstado) {
		super();
		this.idEstado = id_estado;
		this.tituloEstado = tituloEstado;
	}

	public int getId_estado() {
		return idEstado;
	}

	public void setId_estado(int id_estado) {
		this.idEstado = id_estado;
	}

	public String getTituloEstado() {
		return tituloEstado;
	}

	public void setTituloEstado(String tituloEstado) {
		this.tituloEstado = tituloEstado;
	}

}
