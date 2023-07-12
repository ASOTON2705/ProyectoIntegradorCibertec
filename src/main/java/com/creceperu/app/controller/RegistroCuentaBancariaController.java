package com.creceperu.app.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.creceperu.app.controller.dto.CuentaBancariaRegistroDTO;
import com.creceperu.app.model.CuentaBancaria;
import com.creceperu.app.repository.BancoRepository;
import com.creceperu.app.repository.CuentaBancariaRepository;
import com.creceperu.app.service.CuentaBancariaService;
import com.creceperu.app.service.UsuarioServiceImpl.CustomUserDetails;

@Controller
@RequestMapping("/cuentaBancaria")
public class RegistroCuentaBancariaController {
	
	@Autowired
	private CuentaBancariaService cuentaBancariaService;
	
	@Autowired
	private BancoRepository bancoRepository;
	
	@Autowired
	private CuentaBancariaRepository cuentaBancariaRepository;
	
	public RegistroCuentaBancariaController(CuentaBancariaService cuentaBancariaService) {
		super();
		this.cuentaBancariaService = cuentaBancariaService;
	}
	
	@ModelAttribute("cuentaBancaria")
	public CuentaBancariaRegistroDTO retornarNuevoCuentaBancariaDTO() {
		return new CuentaBancariaRegistroDTO();
	}
	
	@GetMapping("/CuentasBancarias")
	public String verEstadoDeCuenta(Model model, Authentication authentication, @RequestParam(defaultValue = "0") int page) {
		int pageSize = 4; // Número de elementos por página
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		Sort sort = Sort.by(Sort.Direction.DESC, "fechaRegistro");
		Pageable pageable = PageRequest.of(page, pageSize, sort);
		Page<CuentaBancaria> cuentasBancariasPage = cuentaBancariaRepository.findByObjUsuarioId(customUserDetails.getId(), pageable);
		model.addAttribute("cuentasBancarias", cuentasBancariasPage.getContent());
		model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", cuentasBancariasPage.getTotalPages());
		return "misCuentasBancarias";
	}
	
	@GetMapping("/registroCuentaBancaria")
	public String mostrarFormularioDeRegistroCuentaBancaria(Model model) {
		model.addAttribute("lstBanco", bancoRepository.findAll());
		return "cuentaBancaria";
	}
	
	@PostMapping("/registroCuentaBancaria")
	public String registrarCuentaBancaria(Model model, @RequestParam("idUsuario") String idUsuario, @ModelAttribute("cuentaBancaria") CuentaBancariaRegistroDTO cuentaBancariaDTO) {
		if(cuentaBancariaRepository.existsByNumeroCuenta(cuentaBancariaDTO.getNumeroCuenta())) {
	        return "redirect:/cuentaBancaria/registroCuentaBancaria?error";
	    }
		Long idusuario = Long.parseLong(idUsuario);
		cuentaBancariaDTO.setId(idusuario);
		cuentaBancariaDTO.setFechaRegistro(new Date());
		cuentaBancariaService.guardar(cuentaBancariaDTO);
		return "redirect:/cuentaBancaria/registroCuentaBancaria?exito";
	}
}
