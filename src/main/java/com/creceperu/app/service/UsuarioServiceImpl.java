package com.creceperu.app.service;

import java.util.Arrays;
import java.util.Collection;import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.creceperu.app.controller.dto.UsuarioRegistroDTO;
import com.creceperu.app.model.Rol;
import com.creceperu.app.model.Saldo;
import com.creceperu.app.model.Usuario;
import com.creceperu.app.repository.RolRepository;
import com.creceperu.app.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RolRepository rolRepository;

	public class CustomUserDetails extends User {
	    public final String nombres;
	    public final String apellidos;
	    public final Long id;

	    public CustomUserDetails(String nombres, String apellidos,Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
	        super(apellidos, password, authorities);
	        this.nombres = nombres;
	        this.apellidos = apellidos;
	        this.id = id;
	    }

	    public String getNombres() {
	        return nombres;
	    }
	    public String getApellidos() {
	        return apellidos;
	    }
	    public Long getId() {
	        return id;
	    }
	}
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}
	@Override
	public Usuario guardar(UsuarioRegistroDTO registroDTO, String role) {
		Rol rol = rolRepository.findByNombre(role);
		Usuario usuario = new Usuario(registroDTO.getNombres(), registroDTO.getApellidos(), registroDTO.getDni(),
				registroDTO.getUbigeo(),registroDTO.getDireccion(), registroDTO.getTelefono(), registroDTO.getEmail(),
				registroDTO.getEmailRecuperacion(), passwordEncoder.encode(registroDTO.getPassword()), registroDTO.getFechaIngreso(),
				registroDTO.getEstado());
		usuario.getRoles().add(rol);
		return usuarioRepository.save(usuario);
	} 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(username);
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuario o password inválidos");
		}
		return new CustomUserDetails(usuario.getNombres(), usuario.getApellidos(), usuario.getId(),
				usuario.getEmail(), usuario.getPassword(), mapearAutoridadesRoles(usuario.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
	}

	@Override
	public List<Usuario> listarUsuarios() {
		return usuarioRepository.findAll();
	}

	@Override
	public void init() {

	}
}
