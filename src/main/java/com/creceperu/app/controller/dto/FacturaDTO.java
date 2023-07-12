package com.creceperu.app.controller.dto;

import java.util.Date;

import lombok.Data;

@Data
public class FacturaDTO {
	private String id_factura;
	private String desc_factura;
	private Date fecharegistro;
	private Date fechavencimiento;
	private double monto;
	private String estado;
	private String ruc;
	private Long id;
	
	public FacturaDTO(String id_factura, String desc_factura, Date fecharegistro, Date fechavencimiento, double monto,
			String estado, String ruc, Long id) {
		super();
		this.id_factura = id_factura;
		this.desc_factura = desc_factura;
		this.fecharegistro = fecharegistro;
		this.fechavencimiento = fechavencimiento;
		this.monto = monto;
		this.estado = estado;
		this.ruc = ruc;
		this.id = id;
	}
	
	public FacturaDTO(String desc_factura, Date fecharegistro, Date fechavencimiento, double monto, String estado,
			String ruc, Long id) {
		super();
		this.desc_factura = desc_factura;
		this.fecharegistro = fecharegistro;
		this.fechavencimiento = fechavencimiento;
		this.monto = monto;
		this.estado = estado;
		this.ruc = ruc;
		this.id = id;
	}
	
	public FacturaDTO() {
	}
	
}
