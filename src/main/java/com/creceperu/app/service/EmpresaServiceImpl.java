package com.creceperu.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creceperu.app.controller.dto.EmpresaDTO;
import com.creceperu.app.model.Empresa;
import com.creceperu.app.repository.EmpresaRepository;

@Service
public class EmpresaServiceImpl implements EmpresaService{
	@Autowired
	private EmpresaRepository empresaReposoitory;
	
	@Override
	public Empresa guardar(EmpresaDTO empresaDTO) {
		Empresa empresa = new Empresa(empresaDTO.getRuc(), empresaDTO.getRazonsocial(), empresaDTO.getNombre_comercial(), 
				empresaDTO.getDirec_fiscal(), empresaDTO.getActividad_economica(), empresaDTO.getTelefono(), empresaDTO.getEmail(), 
				empresaDTO.getRep_legal(), empresaDTO.getFecha_registro(), empresaDTO.getEstado());
		return empresaReposoitory.save(empresa);
	}
}
