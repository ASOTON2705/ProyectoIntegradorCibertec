package com.creceperu.app.model;

import lombok.Data;

@Data
public class OportunidadesXFiltroResult {
	
	private String id_oportunidad;
	
	private String estado;
	
	private String calificacion;
	
	private String razonsocial;
	
	private String fecharegistro;
	
	private String fechaPago;
	
	private double monto;
	
	private double monto_disponible;
	
	private double rendimiento;
	
	private double tasa_riesgo;

	public OportunidadesXFiltroResult(String id_oportunidad, String estado, String calificacion, String razonsocial,
			String fecharegistro, String fechaPago, double monto, double monto_disponible, double rendimiento,
			double tasa_riesgo) {
		super();
		this.id_oportunidad = id_oportunidad;
		this.estado = estado;
		this.calificacion = calificacion;
		this.razonsocial = razonsocial;
		this.fecharegistro = fecharegistro;
		this.fechaPago = fechaPago;
		this.monto = monto;
		this.monto_disponible = monto_disponible;
		this.rendimiento = rendimiento;
		this.tasa_riesgo = tasa_riesgo;
	}
	
}
