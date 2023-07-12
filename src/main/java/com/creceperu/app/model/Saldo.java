package com.creceperu.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table( name = "saldo")
public class Saldo {
	/*@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_saldo;*/
	@Id
	private Long id;
	
	/*@OneToOne
	*/
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Usuario usuario;
	
	@Column(name = "saldo")
	private Double saldo;

	public Saldo(Long id, Usuario usuario, Double saldo) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.saldo = saldo;
	}

	public Saldo(Usuario usuario, Double saldo) {
		super();
		this.usuario = usuario;
		this.saldo = saldo;
	}

	public Saldo(Long id, Double saldo) {
		super();
		this.id = id;
		this.saldo = saldo;
	}
	
	public Saldo() {
	}
	
}
