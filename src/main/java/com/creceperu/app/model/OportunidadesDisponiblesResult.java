package com.creceperu.app.model;

import lombok.Data;

@Data
public class OportunidadesDisponiblesResult {
	private int oportunidadesDisponibles;
    private double montoOportunidadesDisponibles;

    public OportunidadesDisponiblesResult(int oportunidadesDisponibles, double montoOportunidadesDisponibles) {
        this.oportunidadesDisponibles = oportunidadesDisponibles;
        this.montoOportunidadesDisponibles = montoOportunidadesDisponibles;
    }
}
