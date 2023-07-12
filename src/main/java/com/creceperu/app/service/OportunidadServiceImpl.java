package com.creceperu.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creceperu.app.controller.dto.OportunidadDTO;
import com.creceperu.app.model.Oportunidad;
import com.creceperu.app.repository.OportunidadRepository;

@Service
public class OportunidadServiceImpl implements OportunidadService{
	@Autowired
	private OportunidadRepository oportunidadRepository;
	
	@Override
	public Oportunidad guardar(OportunidadDTO oportunidadDTO) {
		Oportunidad oportunidad = new Oportunidad(oportunidadDTO.getId_oportunidad(), oportunidadDTO.getCalificacion(), 
				oportunidadDTO.getRendimiento(), oportunidadDTO.getTasa_riesgo(),oportunidadDTO.getMonto(), oportunidadDTO.getMonto_disponible(),
				oportunidadDTO.getFechaPago(), oportunidadDTO.getFecharegistro(),oportunidadDTO.getId_empresa(), 
				oportunidadDTO.getEstado());
		return oportunidadRepository.save(oportunidad);
	}
	
	@Override
	public List<Oportunidad> listarOportunidad(){
		return oportunidadRepository.findAll();
	}
}
