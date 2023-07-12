package com.creceperu.app.controller.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OportunidadDTO {
	
	private String id_oportunidad;
	private String calificacion;
	private double rendimiento;
	private double tasa_riesgo;
	private double monto;
	private double monto_disponible;
	private Date fechaPago;
	private Date fecharegistro;
	private Integer id_empresa;
	private String estado;
	
	public OportunidadDTO(String id_oportunidad, String calificacion, double rendimiento, double tasa_riesgo,
			double monto, double monto_disponible, Date fechaPago, Date fecharegistro, Integer id_empresa, String estado) {
		super();
		this.id_oportunidad = id_oportunidad;
		this.calificacion = calificacion;
		this.rendimiento = rendimiento;
		this.tasa_riesgo = tasa_riesgo;
		this.monto = monto;
		this.monto_disponible = monto_disponible;
		this.fechaPago = fechaPago;
		this.fecharegistro = fecharegistro;
		this.id_empresa = id_empresa;
		this.estado = estado;
	}
	
	public OportunidadDTO(String calificacion, double rendimiento, double tasa_riesgo, double monto,
			double monto_disponible, Date fechaPago, Date fecharegistro, Integer id_empresa, String estado) {
		super();
		this.calificacion = calificacion;
		this.rendimiento = rendimiento;
		this.tasa_riesgo = tasa_riesgo;
		this.monto = monto;
		this.monto_disponible = monto_disponible;
		this.fechaPago = fechaPago;
		this.fecharegistro = fecharegistro;
		this.id_empresa = id_empresa;
		this.estado = estado;
	}
	
	public OportunidadDTO() {
	}
	
}

