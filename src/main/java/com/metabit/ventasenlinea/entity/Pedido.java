package com.metabit.ventasenlinea.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {
	
	@Id
	@GeneratedValue
	@Column(name = "id_pedido", nullable = false)
	private int idPedido;
	
	@Column(name = "direccion_destino")
	private String direccionDestino;
	
	@Column(name = "fecha_pedido")
	private Date fechaPedido;
	
	//llaves foraneas
	@ManyToOne
	private Pais pais;
	
	@ManyToOne
	private Estado estado;
	
	@ManyToOne
	private Cliente cliente;
	
	@OneToMany(mappedBy="pedido" )
	private List<ArticuloPedido> listaArticulo=new ArrayList<ArticuloPedido>();
	
	public Pedido() {
		super();
	}

	public Pedido(int id_pedido, String direccion_destino, Date fecha_pedido, Pais pais, Estado estado) {
		super();
		this.idPedido = id_pedido;
		this.direccionDestino = direccion_destino;
		this.fechaPedido = fecha_pedido;
		this.pais = pais;
		this.estado = estado;
	}

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public String getDireccionDestino() {
		return direccionDestino;
	}

	public void setDireccionDestino(String direccionDestino) {
		this.direccionDestino = direccionDestino;
	}

	public Date getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ArticuloPedido> getListaArticulo() {
		return listaArticulo;
	}

	public void setListaArticulo(List<ArticuloPedido> listaArticulo) {
		this.listaArticulo = listaArticulo;
	}

	@Override
	public String toString() {
		return "Pedido [idPedido=" + idPedido + ", direccionDestino=" + direccionDestino + ", fechaPedido="
				+ fechaPedido + ", pais=" + pais + ", estado=" + estado + ", cliente=" + cliente + ", listaArticulo="
				+ listaArticulo + "]";
	}
	
}
