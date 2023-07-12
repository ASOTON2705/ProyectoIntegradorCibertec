package com.creceperu.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creceperu.app.controller.dto.MovimientoRegistroDTO;
import com.creceperu.app.model.Movimiento;
import com.creceperu.app.repository.MovimientoRepository;

@Service
public class MovimientoServiceImpl implements MovimientoService{
	@Autowired
	private MovimientoRepository movimientoRepository;
	
	@Override
	public Movimiento guardar(MovimientoRegistroDTO movimientoRegistroDTO) {
		Movimiento movimiento = new Movimiento(movimientoRegistroDTO.getId_movimiento(), movimientoRegistroDTO.getMonto(), 
				movimientoRegistroDTO.getTipoMovimiento(), movimientoRegistroDTO.getFechaMovimiento(), movimientoRegistroDTO.getId(), 
				movimientoRegistroDTO.getId_cuentaBancaria());
		return movimientoRepository.save(movimiento);
	}
	
	@Override
	public List<Movimiento> listarMovimientos(){
		return movimientoRepository.findAll();
	}
}
