package com.creceperu.app.service;

import java.util.List;

import com.creceperu.app.controller.dto.OportunidadDTO;
import com.creceperu.app.model.Oportunidad;

public interface OportunidadService {
	public Oportunidad guardar(OportunidadDTO oportunidadDTO);
	public List<Oportunidad> listarOportunidad();
}
