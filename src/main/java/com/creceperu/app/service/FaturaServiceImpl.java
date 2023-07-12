package com.creceperu.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creceperu.app.controller.dto.FacturaDTO;
import com.creceperu.app.model.Factura;
import com.creceperu.app.repository.FacturaRepository;

@Service
public class FaturaServiceImpl implements FacturaService{
	@Autowired
	private FacturaRepository facturaRepository;
	
	@Override
	public Factura guardar(FacturaDTO facturaDTO) {
		Factura factura = new Factura(facturaDTO.getId_factura(), facturaDTO.getDesc_factura(), facturaDTO.getFecharegistro(),
				facturaDTO.getFechavencimiento(), facturaDTO.getMonto(), facturaDTO.getEstado(),facturaDTO.getRuc(), facturaDTO.getId());
		return facturaRepository.save(factura);
	}
	
	@Override
	public List<Factura> listarFacturas(){
		return facturaRepository.findAll();
	}
}
