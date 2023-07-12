package com.creceperu.app.model;

import lombok.Data;

@Data
public class OportunidadesPagadasResult {
	private int oportunidadesPagadas;
    private double montoOportunidadesPagadas;

    public OportunidadesPagadasResult(int oportunidadesPagadas, double montoOportunidadesPagadas) {
        this.oportunidadesPagadas = oportunidadesPagadas;
        this.montoOportunidadesPagadas = montoOportunidadesPagadas;
    }
}
