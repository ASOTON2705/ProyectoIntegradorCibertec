package com.creceperu.app.service;

import java.util.List;

import com.creceperu.app.controller.dto.MovimientoRegistroDTO;
import com.creceperu.app.model.Movimiento;

public interface MovimientoService {
	
	public Movimiento guardar(MovimientoRegistroDTO movimientoRegistroDTO);
	
	public List<Movimiento> listarMovimientos();
	
}
