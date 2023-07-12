package com.creceperu.app.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "oportunidadFactura")
public class OportunidadFactura {
	
	@EmbeddedId
    private OportunidadFacturaId id;
	
	@ManyToOne
	@JoinColumn(name = "oportunidad_id", referencedColumnName = "id_oportunidad",insertable = false, updatable = false)
	private Oportunidad objOportunidad;
	
	@OneToOne
	@JoinColumn(name = "factura_id", referencedColumnName = "id_factura", insertable = false, updatable = false)
	private Factura objFactura;
	
	public OportunidadFactura(OportunidadFacturaId id) {
		super();
		this.id = id;
	}
	public OportunidadFactura() {
	}
	
}
