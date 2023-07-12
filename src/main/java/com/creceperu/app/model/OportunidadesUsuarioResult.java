package com.creceperu.app.model;

import lombok.Data;

@Data
public class OportunidadesUsuarioResult {
	private int numeroOportunidadesInvertidas;
    private double montoTotalInvertido;

    public OportunidadesUsuarioResult(int numeroOportunidadesInvertidas, double montoTotalInvertido) {
        this.numeroOportunidadesInvertidas = numeroOportunidadesInvertidas;
        this.montoTotalInvertido = montoTotalInvertido;
    }
}
