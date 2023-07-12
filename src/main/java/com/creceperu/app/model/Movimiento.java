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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "movimiento")
public class Movimiento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_movimiento;
	
	@Column(name ="monto")
	private Double monto;
	
	@Column(name = "tipoMovimiento")
	private String tipoMovimiento;
	
	@Column(name = "fechaMovimiento")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date fechaMovimiento;
	
	private Long id;
	@ManyToOne
	@JoinColumn(name = "id", insertable = false, updatable = false)
	private Usuario objUsuario;
	
	private Integer id_cuentaBancaria;
	@ManyToOne
	@JoinColumn(name = "id_cuentaBancaria", insertable = false, updatable = false)
	private CuentaBancaria objCuentaBancaria;
	
	public Movimiento(Integer id_movimiento, Double monto, String tipoMovimiento, Date fechaMovimiento, Long id, Integer id_cuentaBancaria) {
		super();
		this.id_movimiento = id_movimiento;
		this.monto = monto;
		this.tipoMovimiento = tipoMovimiento;
		this.fechaMovimiento = fechaMovimiento;
		this.id = id;
		this.id_cuentaBancaria = id_cuentaBancaria;
	}
	
	public Movimiento(Double monto, String tipoMovimiento, Date fechaMovimiento, Long id, Integer id_cuentaBancaria) {
		super();
		this.monto = monto;
		this.tipoMovimiento = tipoMovimiento;
		this.fechaMovimiento = fechaMovimiento;
		this.id = id;
		this.id_cuentaBancaria = id_cuentaBancaria;
	}
	
	public Movimiento() {
		
	}
}
