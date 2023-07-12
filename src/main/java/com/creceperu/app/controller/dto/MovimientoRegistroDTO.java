package com.creceperu.app.controller.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MovimientoRegistroDTO {
	private Integer id_movimiento;
	private Double monto;
	private String tipoMovimiento;
	private Date fechaMovimiento;
	private Long id;
	private Integer id_cuentaBancaria;
	
	public MovimientoRegistroDTO(Integer id_movimiento, Double monto, String tipoMovimiento, Date fechaMovimiento, Long id, Integer id_cuentaBancaria) {
		super();
		this.id_movimiento = id_movimiento;
		this.monto = monto;
		this.tipoMovimiento = tipoMovimiento;
		this.fechaMovimiento = fechaMovimiento;
		this.id = id;
		this.id_cuentaBancaria = id_cuentaBancaria;
	}
	
	public MovimientoRegistroDTO() {
		
	}
}
