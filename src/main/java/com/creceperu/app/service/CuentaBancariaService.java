package com.creceperu.app.service;

import java.util.List;

import com.creceperu.app.controller.dto.CuentaBancariaRegistroDTO;
import com.creceperu.app.model.CuentaBancaria;

public interface CuentaBancariaService{
	
	public CuentaBancaria guardar(CuentaBancariaRegistroDTO cuentaBancariaDTO);
	
	public List<CuentaBancaria> listarCuentas();
	
}
