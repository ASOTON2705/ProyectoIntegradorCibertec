package com.creceperu.app.controller.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CuentaBancariaRegistroDTO {
	private Integer id_cuentaBancaria;
	private String numeroCuenta;
	private Double monto;
	private Integer id_banco;
	private Long id;
	private Date fechaRegistro;
	
	public CuentaBancariaRegistroDTO(Integer id_cuentaBancaria, String numeroCuenta, Double monto, Integer id_banco, Long id, Date fechaRegistro) {
		super();
		this.id_cuentaBancaria = id_cuentaBancaria;
		this.numeroCuenta = numeroCuenta;
		this.monto = monto;
		this.id_banco = id_banco;
		this.id = id;
		this.fechaRegistro = fechaRegistro;
	}
	
	public CuentaBancariaRegistroDTO() {
		
	}
}
