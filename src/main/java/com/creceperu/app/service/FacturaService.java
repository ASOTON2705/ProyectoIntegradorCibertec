package com.creceperu.app.service;

import java.util.List;

import com.creceperu.app.controller.dto.FacturaDTO;
import com.creceperu.app.model.Factura;

public interface FacturaService {
	public Factura guardar(FacturaDTO facturaDTO);
	public List<Factura> listarFacturas();
}
