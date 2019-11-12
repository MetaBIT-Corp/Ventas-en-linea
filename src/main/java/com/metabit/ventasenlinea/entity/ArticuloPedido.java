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
	@Column(name = "id_articulo_pedido", nullable = false)
	private int idArticuloPedido;

	@Column(name = "cantidad", nullable = false)
	private int cantidad;

	@Column(name = "precio_unitario", nullable = false)
	private float precioUnitario;

	// foraneas
	@ManyToOne
	private Pedido pedido;

	@ManyToOne
	private Producto producto;

	public ArticuloPedido() {
		super();
	}

	public ArticuloPedido(int idArticuloPedido, int cantidad, float precioUnitario, Pedido pedido, Producto producto) {
		super();
		this.idArticuloPedido = idArticuloPedido;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.pedido = pedido;
		this.producto = producto;
	}

	public int getIdArticuloPedido() {
		return idArticuloPedido;
	}

	public void setIdArticuloPedido(int idArticuloPedido) {
		this.idArticuloPedido = idArticuloPedido;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public float getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(float precioUnitario) {
		this.precioUnitario = precioUnitario;
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
