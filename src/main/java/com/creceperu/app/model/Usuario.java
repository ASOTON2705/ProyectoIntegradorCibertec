package com.creceperu.app.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombres")
	private String nombres;

	@Column(name = "apellidos")
	private String apellidos;

	@Column(name = "dni")
	private String dni;

	@Column(name = "ubigeo")
	private String ubigeo;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "telefono")
	private String telefono;

	private String email;

	@Column(name = "emailRecuperacion")
	private String emailRecuperacion;

	private String password;
	
	@Column(name = "fechaIngreso")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date fechaIngreso;
	
	@Column(name = "estado")
	private int estado;
	
	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Saldo objSaldo;
	
	@PostPersist
    public void crearSaldo() {
        Saldo nuevoSaldo = new Saldo();
        nuevoSaldo.setUsuario(this);
        nuevoSaldo.setSaldo(0.0);
        this.objSaldo = nuevoSaldo;
    }

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))
	private Collection<Rol> roles = new ArrayList<>();

	public Usuario(Long id, String nombres, String apellidos, String dni, String ubigeo, String direccion, String telefono,
			String email, String emailRecuperacion, String password, Date fechaIngreso, int estado) {
		super();
		this.id = id;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.dni = dni;
		this.ubigeo = ubigeo;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.emailRecuperacion = emailRecuperacion;
		this.password = password;
		this.fechaIngreso = fechaIngreso;
		this.estado = estado;
	}
	
	public Usuario(String nombres, String apellidos, String dni, String ubigeo, String direccion, String telefono, String email,
			String emailRecuperacion, String password, Date fechaIngreso, int estado) {
		super();
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.dni = dni;
		this.ubigeo = ubigeo;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.emailRecuperacion = emailRecuperacion;
		this.password = password;
		this.fechaIngreso = fechaIngreso;
		this.estado = estado;
	}
	
	public Usuario() {

	}

}
