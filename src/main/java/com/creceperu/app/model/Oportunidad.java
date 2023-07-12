package com.creceperu.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "oportunidad")
public class Oportunidad {
	
	@Id
	private String id_oportunidad;
	
	@Column(name = "calificacion")
	private String calificacion;
	
	@Column(name = "rendimiento")
	private double rendimiento;
	
	@Column(name = "tasa_riesgo")
	private double tasa_riesgo;
	
	@Column(name = "monto")
	private double monto;
	
	@Column(name = "monto_disponible")
	private double monto_disponible;
	
	@Column(name = "fechaPago")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaPago;
	
	@Column(name = "fecharegistro")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date fecharegistro;
	
	private Integer id_empresa;
	@ManyToOne
	@JoinColumn(name = "id_empresa", insertable = false, updatable = false)
	private Empresa objEmpresa;
	
	private String estado;

	public Oportunidad(String id_oportunidad, String calificacion, double rendimiento, double tasa_riesgo,
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

	public Oportunidad(String calificacion, double rendimiento, double tasa_riesgo, double monto,
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

	public Oportunidad() {
	}
	
}
