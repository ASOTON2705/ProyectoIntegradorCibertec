package com.creceperu.app.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "banco")
public class Banco {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_banco;
	private String nombre_banco;
	
	public Banco(Integer id_banco, String nombre_banco) {
		super();
		this.id_banco = id_banco;
		this.nombre_banco = nombre_banco;
	}

	public Banco() {
		
	}

	public Banco(String nombre_banco) {
		super();
		this.nombre_banco = nombre_banco;
	}
	
}
