package com.creceperu.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.Data;

@Data
@Entity
@Table(name = "cuentaBancaria")
public class CuentaBancaria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_cuentaBancaria;
	
	@Column(name = "numeroCuenta")
	private String numeroCuenta;
	
	@Column(name = "monto")
	private Double monto;
	
	private Integer id_banco;
	@ManyToOne
	@JoinColumn(name = "id_banco", insertable = false, updatable = false)
	private Banco objBanco;
	
	private Long id;
	@ManyToOne
	@JoinColumn(name = "id", insertable = false, updatable = false)
	private Usuario objUsuario;
	
	@Column(name = "fechaRegistro")
	private Date fechaRegistro;
	
	public CuentaBancaria(Integer id_cuentaBancaria, String numeroCuenta, Double monto, Integer id_banco, Long id, Date fechaRegistro) {
		super();
		this.id_cuentaBancaria = id_cuentaBancaria;
		this.numeroCuenta = numeroCuenta;
		this.monto = monto;
		this.id_banco = id_banco;
		this.id = id;
		this.fechaRegistro = fechaRegistro;
	}
	
	public CuentaBancaria(String numeroCuenta, Double monto, Integer id_banco, Long id, Date fechaRegistro) {
		super();
		this.numeroCuenta = numeroCuenta;
		this.monto = monto;
		this.id_banco = id_banco;
		this.id = id;
		this.fechaRegistro = fechaRegistro;
	}
	
	public CuentaBancaria() {
		
	}
}
