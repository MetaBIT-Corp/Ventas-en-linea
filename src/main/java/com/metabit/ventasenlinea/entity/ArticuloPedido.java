package com.metabit.ventasenlinea.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "articulo_pedido")
public class ArticuloPedido {

	@Id
	@GeneratedValue
	@Column(name = "id_articulo_pedido" , nullable = false)
	private int id_articulo_pedido;
	
	@Column(name = "cantidad" , nullable = false)
	private int cantidad;
	
	@Column(name= "precio_unitario", nullable = false)
	private float precio_unitario;
	
	//foraneas
	 @ManyToOne
	 private Pedido pedido;
	 
	 @ManyToOne 
	 private Producto producto;
	 
	public ArticuloPedido() {
		super();
	}

	public ArticuloPedido(int id_articulo_pedido, int cantidad, float precio_unitario, Pedido pedido,
			Producto producto) {
		super();
		this.id_articulo_pedido = id_articulo_pedido;
		this.cantidad = cantidad;
		this.precio_unitario = precio_unitario;
		this.pedido = pedido;
		this.producto = producto;
	}

	public int getId_articulo_pedido() {
		return id_articulo_pedido;
	}

	public void setId_articulo_pedido(int id_articulo_pedido) {
		this.id_articulo_pedido = id_articulo_pedido;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public float getPrecio_unitario() {
		return precio_unitario;
	}

	public void setPrecio_unitario(float precio_unitario) {
		this.precio_unitario = precio_unitario;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	 	 
}
