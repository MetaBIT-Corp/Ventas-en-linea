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
	@Column(name = "id_estado", nullable = false)
	private int id_estado;
	
	@Column(name = "titulo_estado", nullable = false)
	private String titulo_estado;

	public Estado() {
		super();
	}

	public Estado(int id_estado, String titulo_estado) {
		super();
		this.id_estado = id_estado;
		this.titulo_estado = titulo_estado;
	}

	public int getId_estado() {
		return id_estado;
	}

	public void setId_estado(int id_estado) {
		this.id_estado = id_estado;
	}

	public String getTitulo_estado() {
		return titulo_estado;
	}

	public void setTitulo_estado(String titulo_estado) {
		this.titulo_estado = titulo_estado;
	}

	
}
