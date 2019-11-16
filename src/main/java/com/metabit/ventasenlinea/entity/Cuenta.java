package com.metabit.ventasenlinea.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cuentas")
public class Cuenta {

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "numero_tarjeta", nullable = false)
	private String numeroTarjeta;

	@Column(name = "codigo", nullable = false)
	private int codigo;

	@Column(name = "saldo", nullable = false)
	private double saldo;

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;
	
	@Column(name="fecha_de_vencimiento")
	private Date fechaDeVencimiento;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getFechaDeVencimiento() {
		return fechaDeVencimiento;
	}

	public void setFechaDeVencimiento(Date fechaDeVencimiento) {
		this.fechaDeVencimiento = fechaDeVencimiento;
	}

	public Cuenta(int id, String numeroTarjeta, int codigo, double saldo, Cliente cliente, Date fechaDeVencimiento) {
		super();
		this.id = id;
		this.numeroTarjeta = numeroTarjeta;
		this.codigo = codigo;
		this.saldo = saldo;
		this.cliente = cliente;
		this.fechaDeVencimiento = fechaDeVencimiento;
	}

	public Cuenta() {
	}
}
