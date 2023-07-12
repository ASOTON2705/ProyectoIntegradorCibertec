package com.creceperu.app.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creceperu.app.controller.dto.FacturaDTO;
import com.creceperu.app.repository.EmpresaRepository;
import com.creceperu.app.repository.FacturaRepository;
import com.creceperu.app.service.FacturaService;
import com.creceperu.app.service.UsuarioServiceImpl.CustomUserDetails;

@Controller
@RequestMapping("/registroFactura")
public class RegistroFacturaController {
	
	@Autowired
	private FacturaService facturaService;
	
	@Autowired
	private FacturaRepository facturaRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	public RegistroFacturaController (FacturaService facturaService) {
		super();
		this.facturaService = facturaService;
	}
	
	@ModelAttribute("factura")
	public FacturaDTO facturaDTO() {
		return new FacturaDTO();
	}
	
	@GetMapping
	public String mostrarFomularioDeRegistroFactura(Model model) {
		String lastCode = facturaRepository.getLastGeneratedCode();
        String nextCode = generateNextCode(lastCode);
		model.addAttribute("lstEmpresa", empresaRepository.findAll());
		model.addAttribute("IdFactura", nextCode);
		return "factura";
	}
	
	@PostMapping
	public String registrarFactura(@ModelAttribute("factura") FacturaDTO facturaDTO, Authentication authentication) {
		String lastCode = facturaRepository.getLastGeneratedCode();
        String nextCode = generateNextCode(lastCode);
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		facturaDTO.setId_factura(nextCode);
		facturaDTO.setId(customUserDetails.getId());
		facturaDTO.setFecharegistro(new Date());
		facturaDTO.setEstado("Listada");
		facturaService.guardar(facturaDTO);
		return "redirect:/registroFactura?exito";
	}
	
	private String generateNextCode(String lastCode) {
        if (lastCode == null) {
            return "F000001";
        }
        int lastNumber = Integer.parseInt(lastCode.substring(1));
        int nextNumber = lastNumber + 1;
        String nextNumberWithPadding = String.format("%06d", nextNumber);
        return "F" + nextNumberWithPadding;
    }
}
