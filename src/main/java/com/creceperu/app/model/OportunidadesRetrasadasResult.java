package com.creceperu.app.model;

import lombok.Data;

@Data
public class OportunidadesRetrasadasResult {
	private int oportunidadesRetrasadas;
    private double montoOportunidadesRetrasadas;

    public OportunidadesRetrasadasResult(int oportunidadesRetrasadas, double montoOportunidadesRetrasadas) {
        this.oportunidadesRetrasadas = oportunidadesRetrasadas;
        this.montoOportunidadesRetrasadas = montoOportunidadesRetrasadas;
    }
}
