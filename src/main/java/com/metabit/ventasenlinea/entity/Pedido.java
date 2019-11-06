package com.metabit.ventasenlinea.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {
	
	@Id
	@GeneratedValue
	@Column(name = "id_pedido", nullable = false)
	private int id_pedido;
	
	@Column(name = "direccion_destino")
	private String direccion_destino;
	
	@Column(name = "fecha_pedido")
	private Date fecha_pedido;
	
	//llaves foraneas
	@ManyToOne
	private Pais pais;
	
	@ManyToOne
	private Estado estado;;
	
	public Pedido() {
		super();
	}

	public Pedido(int id_pedido, String direccion_destino, Date fecha_pedido, Pais pais, Estado estado) {
		super();
		this.id_pedido = id_pedido;
		this.direccion_destino = direccion_destino;
		this.fecha_pedido = fecha_pedido;
		this.pais = pais;
		this.estado = estado;
	}

	public int getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(int id_pedido) {
		this.id_pedido = id_pedido;
	}

	public String getDireccion_destino() {
		return direccion_destino;
	}

	public void setDireccion_destino(String direccion_destino) {
		this.direccion_destino = direccion_destino;
	}

	public Date getFecha_pedido() {
		return fecha_pedido;
	}

	public void setFecha_pedido(Date fecha_pedido) {
		this.fecha_pedido = fecha_pedido;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
}
