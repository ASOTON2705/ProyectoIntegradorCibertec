package com.creceperu.app.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class OportunidadFacturaId implements Serializable {
    private String oportunidad_id;
    private String factura_id;
}
