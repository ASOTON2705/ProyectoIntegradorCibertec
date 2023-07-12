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
@Table(name = "oportunidadUsuario")
public class OportunidadUsuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer oportunidad_usuario_id;
	
	@Column(name = "monto_invertido")
	private double monto_invertido;
	
	@Column(name = "fecha_registro")
	private Date fecha_registro;
	
	@Column(name = "ganancia_esperada")
	private double ganancia_esperada;
	
	@Column(name = "porcentaje_oportunidad")
	private double porcentaje_oportunidad;
	
	private String oportunidad_id;
	@ManyToOne
	@JoinColumn(name = "oportunidad_id", referencedColumnName = "id_oportunidad",insertable = false, updatable = false)
	private Oportunidad objOportunidad;
	
	private Long usuario_id;
	@ManyToOne
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Usuario objUsuario;
	
}

