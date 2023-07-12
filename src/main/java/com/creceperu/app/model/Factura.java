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
@Table(name = "factura")
public class Factura{
	@Id
	private String id_factura;
	
	@Column(name = "desc_factura")
	private String desc_factura;
	
	@Column(name = "fecharegistro")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date fecharegistro;
	
	@Column(name = "fechavencimiento")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechavencimiento;
	
	@Column(name = "monto")
	private double monto;
	
	@Column(name = "estado")
	private String estado;
	
	private String ruc;
	@ManyToOne
	@JoinColumn(name = "ruc", referencedColumnName = "ruc", insertable = false, updatable = false)
	private Empresa objEmpresa;
	
	private Long id;
	@ManyToOne
	@JoinColumn(name = "id", insertable = false, updatable = false)
	private Usuario objUsuario;
	
	public Factura(String id_factura, String desc_factura, Date fecharegistro, Date fechavencimiento,
			double monto, String estado, String ruc, Long id) {
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

	public Factura(String desc_factura, Date fecharegistro, Date fechavencimiento, String ruc,
			double monto, String estado, Long id) {
		super();
		this.desc_factura = desc_factura;
		this.fecharegistro = fecharegistro;
		this.fechavencimiento = fechavencimiento;
		this.monto = monto;
		this.estado = estado;
		this.ruc = ruc;
		this.id = id;
	}

	public Factura() {
	}
	
	
}
