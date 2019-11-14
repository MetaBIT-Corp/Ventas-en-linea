package com.metabit.ventasenlinea.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cargos")
public class Cargo {
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name="titulo_cargo",nullable=false)
	private String tituloCargo;

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTituloCargo() {
		return tituloCargo;
	}

	public void setTituloCargo(String tituloCargo) {
		this.tituloCargo = tituloCargo;
	}

	
	
	public Cargo(int id, String tituloCargo) {
		super();
		this.id = id;
		this.tituloCargo = tituloCargo;
	}

	public Cargo() {
		
	}

	@Override
	public String toString() {
		return "Cargo [id=" + id + ", tituloCargo=" + tituloCargo + "]";
	}

	
	
}
