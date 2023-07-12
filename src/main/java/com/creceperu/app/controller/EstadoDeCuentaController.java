package com.creceperu.app.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.creceperu.app.model.OportunidadUsuario;
import com.creceperu.app.model.OportunidadesUsuarioResult;
import com.creceperu.app.model.Saldo;
import com.creceperu.app.repository.OportunidadUsuarioRepository;
import com.creceperu.app.repository.SaldoRepository;
import com.creceperu.app.service.UsuarioServiceImpl.CustomUserDetails;

@Controller
@RequestMapping("/estadoDeCuenta")
public class EstadoDeCuentaController {
	
	@Autowired
	private SaldoRepository saldoRepository;
	
	@Autowired
	private OportunidadUsuarioRepository oportunidadUsuarioRepository;
	
	@GetMapping
	public String verEstadoDeCuenta(Model model, Authentication authentication, @RequestParam(defaultValue = "0") int page) {
		int pageSize = 4; // Número de elementos por página
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		Saldo saldo = saldoRepository.findById(customUserDetails.getId()).orElse(new Saldo(customUserDetails.getId(), 0.0));
		model.addAttribute("saldo", saldo.getSaldo());
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<OportunidadUsuario> oportunidadesPage = oportunidadUsuarioRepository.findByObjUsuarioId(customUserDetails.getId(), pageable);
		model.addAttribute("oportunidades", oportunidadesPage.getContent());
		model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", oportunidadesPage.getTotalPages());
		List<Object[]> results = oportunidadUsuarioRepository.getOportunidadesUsuario(customUserDetails.getId());
		List<OportunidadesUsuarioResult> oportunidadesUsuario = new ArrayList<>();

		for (Object[] result : results) {
		    int count = ((BigInteger) result[0]).intValue();
		    Double sum = ((Number) result[1]).doubleValue();
		    OportunidadesUsuarioResult oportunidadU = new OportunidadesUsuarioResult(count, sum);
		    oportunidadesUsuario.add(oportunidadU);
		}
		model.addAttribute("oportunidadesUsuario", oportunidadesUsuario);
		return "estadoCuenta";
	}
}
