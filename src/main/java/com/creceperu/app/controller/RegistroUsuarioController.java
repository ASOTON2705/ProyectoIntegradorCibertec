package com.creceperu.app.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.creceperu.app.controller.dto.UsuarioRegistroDTO;
import com.creceperu.app.repository.UsuarioRepository;
import com.creceperu.app.service.UsuarioService;

@Controller
@RequestMapping("/registro")
public class RegistroUsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public RegistroUsuarioController(UsuarioService usuarioService) {
		super();
		this.usuarioService = usuarioService;
	}
	
	@ModelAttribute("usuario")
	public UsuarioRegistroDTO retornarNuevoUsuarioRegistroDTO() {
		return new UsuarioRegistroDTO();
	}

	@GetMapping
	public String mostrarFormularioDeRegistro() {
		return "registro";
	}
	
	@PostMapping
	public String registrarCuentaDeUsuario(@ModelAttribute("usuario") UsuarioRegistroDTO registroDTO, Model model) {
		if(usuarioRepository.findByEmail(registroDTO.getEmail()) != null) {
	        model.addAttribute("errorEmail", "El correo ya está registrado");
	        return "registro";
	    }
	    if(usuarioRepository.findByDni(registroDTO.getDni()) != null) {
	        model.addAttribute("errorDni", "El DNI ya está registrado");
	        return "registro";
	    }
	    registroDTO.setFechaIngreso(new Date());
	    registroDTO.setEstado(1);
	    usuarioService.guardar(registroDTO, "ROLE_USER");
	    return "redirect:/registro?exito";
	}
}
