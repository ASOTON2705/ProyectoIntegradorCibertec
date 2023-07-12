package com.creceperu.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table( name = "empresa")
public class Empresa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_empresa;
	
	@Column(name = "ruc")
	private String ruc;
	
	@Column(name = "razonsocial")
	private String razonsocial;
	
	@Column(name = "nombre_comercial")
	private String nombre_comercial;
	
	@Column(name = "direc_fiscal")
	private String direc_fiscal;
	
	@Column(name = "actividad_economica")
	private String actividad_economica;
	
	@Column(name = "telefono")
	private Integer telefono;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "rep_legal")
	private String rep_legal;
	
	@Column(name = "fecha_registro")
	private Date fecha_registro;
	
	@Column(name = "estado")
	private String estado;

	public Empresa(Integer id_empresa, String ruc, String razonsocial, String nombre_comercial, String direc_fiscal,
			String actividad_economica, Integer telefono, String email, String rep_legal, Date fecha_registro,
			String estado) {
		super();
		this.id_empresa = id_empresa;
		this.ruc = ruc;
		this.razonsocial = razonsocial;
		this.nombre_comercial = nombre_comercial;
		this.direc_fiscal = direc_fiscal;
		this.actividad_economica = actividad_economica;
		this.telefono = telefono;
		this.email = email;
		this.rep_legal = rep_legal;
		this.fecha_registro = fecha_registro;
		this.estado = estado;
	}

	public Empresa(String ruc, String razonsocial, String nombre_comercial, String direc_fiscal,
			String actividad_economica, Integer telefono, String email, String rep_legal, Date fecha_registro,
			String estado) {
		super();
		this.ruc = ruc;
		this.razonsocial = razonsocial;
		this.nombre_comercial = nombre_comercial;
		this.direc_fiscal = direc_fiscal;
		this.actividad_economica = actividad_economica;
		this.telefono = telefono;
		this.email = email;
		this.rep_legal = rep_legal;
		this.fecha_registro = fecha_registro;
		this.estado = estado;
	}

	public Empresa() {
	}
	
}
