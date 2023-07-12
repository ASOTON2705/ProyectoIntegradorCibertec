package com.creceperu.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creceperu.app.controller.dto.CuentaBancariaRegistroDTO;
import com.creceperu.app.model.CuentaBancaria;
import com.creceperu.app.repository.CuentaBancariaRepository;

@Service
public class CuentaBancariaImpl implements CuentaBancariaService{
	
	@Autowired
	private CuentaBancariaRepository cuentaBancariaRepository;
	
	@Override
	public CuentaBancaria guardar(CuentaBancariaRegistroDTO cuentaBancariaDTO) {
		CuentaBancaria cuentaBancaria = new CuentaBancaria(cuentaBancariaDTO.getNumeroCuenta(), 
				cuentaBancariaDTO.getMonto(), cuentaBancariaDTO.getId_banco(), cuentaBancariaDTO.getId(), 
				cuentaBancariaDTO.getFechaRegistro());
		return cuentaBancariaRepository.save(cuentaBancaria);
	}
	
	@Override
	public List<CuentaBancaria> listarCuentas(){
		return cuentaBancariaRepository.findAll();
	}
	
}
