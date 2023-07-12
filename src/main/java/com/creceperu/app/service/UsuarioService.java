package com.creceperu.app.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.creceperu.app.controller.dto.UsuarioRegistroDTO;
import com.creceperu.app.model.Usuario;


public interface UsuarioService extends UserDetailsService{

	public Usuario guardar(UsuarioRegistroDTO registroDTO, String role);
	
	public List<Usuario> listarUsuarios();

	public void init();
	
}
