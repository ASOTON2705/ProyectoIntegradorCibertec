package com.creceperu.app.service;

import com.creceperu.app.controller.dto.EmpresaDTO;
import com.creceperu.app.model.Empresa;

public interface EmpresaService {
	
	public Empresa guardar(EmpresaDTO empresaRegistroDTO);
	
}
