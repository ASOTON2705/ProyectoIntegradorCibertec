package com.creceperu.app.controller;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.creceperu.app.model.Empresa;
import com.creceperu.app.model.Oportunidad;
import com.creceperu.app.model.OportunidadUsuario;
import com.creceperu.app.model.OportunidadesDisponiblesResult;
import com.creceperu.app.model.OportunidadesPagadasResult;
import com.creceperu.app.model.OportunidadesRetrasadasResult;
import com.creceperu.app.model.Rol;
import com.creceperu.app.model.Saldo;
import com.creceperu.app.model.Usuario;
import com.creceperu.app.repository.EmpresaRepository;
import com.creceperu.app.repository.OportunidadRepository;
import com.creceperu.app.repository.OportunidadUsuarioRepository;
import com.creceperu.app.repository.RolRepository;
import com.creceperu.app.repository.SaldoRepository;
import com.creceperu.app.repository.UsuarioRepository;
import com.creceperu.app.service.UsuarioServiceImpl.CustomUserDetails;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RolRepository rolRepository;
	
	@Autowired
	private SaldoRepository saldoRepository;
	
	@Autowired
	private OportunidadRepository oportunidadRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private OportunidadUsuarioRepository oportunidadUsuarioRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/login")
	public String iniciarSesion() {
		return "login";
	}
	
	@GetMapping("/")
	public String verPaginaConFiltro(Model model, Authentication authentication, @RequestParam(required = false, defaultValue = "") String filtro,
			@RequestParam(defaultValue = "0") int page) {
		int pageSize = 8; // Número de elementos por página
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		Saldo saldo = saldoRepository.findById(customUserDetails.getId()).orElse(new Saldo(customUserDetails.getId(), 0.0));
		model.addAttribute("saldo", saldo.getSaldo());
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<Oportunidad> oportunidadesPage = oportunidadRepository.findOportunidadesXFiltro(filtro, pageable);
		model.addAttribute("lstOportunidades", oportunidadesPage.getContent());
		model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", oportunidadesPage.getTotalPages());
	 // Agregar los parámetros de búsqueda al modelo para mantenerlos en la vista
        model.addAttribute("filtro", filtro);
		return "principal";
	}
	@ResponseBody
	public String verSaldo(Model model, Authentication authentication) {
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		Saldo saldo = saldoRepository.findById(customUserDetails.getId()).orElse(new Saldo(customUserDetails.getId(), 0.0));
		model.addAttribute("saldo", saldo.getSaldo());
		return "layout";
	}
	@GetMapping("/perfilUsuario")
	public String cargarUsuario(@ModelAttribute Usuario usuario, Model model, Authentication authentication) {
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		model.addAttribute("usuario", usuarioRepository.findById(customUserDetails.getId()));
		return "perfilUsuario";
	}
	
	@PostMapping("/perfilUsuario")
	public String actualizarUsuario(@RequestParam("fechaIngreso") String fechaIngreso, 
	    @ModelAttribute Usuario usuario, @RequestParam(value = "rolesSeleccionados", required = false) List<String> rolesSeleccionados, 
	    @RequestParam("userPassword") String userPassword, Model model) throws ParseException {
		if (!passwordEncoder.matches(userPassword, usuario.getPassword())) {
			model.addAttribute("error", "La contraseña ingresada no coincide con la de la sesión");
	        return "perfilUsuario";
		}
	    DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date fecha = formatoFecha.parse(fechaIngreso);
	    usuario.setFechaIngreso(fecha);
	    List<Rol> rolesActuales = new ArrayList<>(usuarioRepository.findById(usuario.getId()).get().getRoles());
	    usuarioRepository.save(usuario);
	    if (rolesSeleccionados != null) {
	        List<Rol> nuevosRoles = new ArrayList<>();
	        for (String rol : rolesSeleccionados) {
	            nuevosRoles.add(rolRepository.findByNombre(rol));
	        }
	        usuario.setRoles(nuevosRoles);
	    } else {
	        usuario.setRoles(rolesActuales);
	    }
	    usuarioRepository.save(usuario);
	    Saldo saldo = saldoRepository.findById(usuario.getId()).orElse(null);
	    if (saldo == null) {
	        saldo = new Saldo(usuario, 0.0);
	    }
	    saldoRepository.save(saldo);
	    return "redirect:/perfilUsuario?exito";
	}
	
	@ModelAttribute("OportunidadUsuario")
	public OportunidadUsuario retornarOportunidadUsuario() {
		return new OportunidadUsuario();
	}
	
	@GetMapping("/verOportunidad")
	public String verOportunidadDetalle(@RequestParam("idOportunidad") String idOportunidad, 
			@RequestParam("razonsocial") String razonsocial,Model model, Authentication authentication) {
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		Saldo saldo = saldoRepository.findById(customUserDetails.getId()).orElse(new Saldo(customUserDetails.getId(), 0.0));
		model.addAttribute("saldo", saldo.getSaldo());
		Oportunidad oportunidad = oportunidadRepository.findByOportunidadId(idOportunidad);
		model.addAttribute("oportunidadSelecionada", oportunidad);
		Empresa empresa = empresaRepository.findByRazonsocial(razonsocial);
		model.addAttribute("empresaSeleccionada", empresa);
		List<Object[]> results = oportunidadRepository.getOportunidadesDisponibles(razonsocial);
		List<OportunidadesDisponiblesResult> oportunidadesDisponibles = new ArrayList<>();

		for (Object[] result : results) {
		    int count = ((BigInteger) result[0]).intValue();
		    Double sum = ((Number) result[1]).doubleValue();
		    OportunidadesDisponiblesResult oportunidadDR = new OportunidadesDisponiblesResult(count, sum);
		    oportunidadesDisponibles.add(oportunidadDR);
		}
		model.addAttribute("oportunidadesDisponibles", oportunidadesDisponibles);
		
		List<Object[]> results2 = oportunidadRepository.getOportunidadesRetrasadas(razonsocial);
		List<OportunidadesRetrasadasResult> oportunidadesRetrasadas = new ArrayList<>();

		for (Object[] result : results2) {
		    int count = ((BigInteger) result[0]).intValue();
		    Double sum = ((Number) result[1]).doubleValue();
		    OportunidadesRetrasadasResult oportunidadTR = new OportunidadesRetrasadasResult(count, sum);
		    oportunidadesRetrasadas.add(oportunidadTR);
		}

		model.addAttribute("oportunidadesRetrasadas", oportunidadesRetrasadas);
		
		List<Object[]> results3 = oportunidadRepository.getOportunidadesPagadas(razonsocial);
		List<OportunidadesPagadasResult> oportunidadesPagadas = new ArrayList<>();

		for (Object[] result : results3) {
		    int count = ((BigInteger) result[0]).intValue();
		    Double sum = ((Number) result[1]).doubleValue();
		    OportunidadesPagadasResult oportunidadPR = new OportunidadesPagadasResult(count, sum);
		    oportunidadesPagadas.add(oportunidadPR);
		}
		
		model.addAttribute("oportunidadesPagadas", oportunidadesPagadas);
		
		Pageable pageable = PageRequest.of(0, 3);
		List<OportunidadUsuario> ultimasInversiones = oportunidadUsuarioRepository.findUltimasInversiones(idOportunidad, pageable);
		model.addAttribute("ultimasInversiones", ultimasInversiones);
		
		Long conteoInversiones = oportunidadUsuarioRepository.contarInversionesPorOportunidadId(idOportunidad);
		model.addAttribute("conteoInversiones", conteoInversiones);
		return "verOportunidad";
	}
	
	@PostMapping("/verOportunidad")
	@Transactional
	public String tomarOportunidad(@RequestParam("Id_Oportunidad") String Id_Oportunidad, @RequestParam("RazonSocial") String RazonSocial,
			@ModelAttribute("OportunidadUsuario") OportunidadUsuario oportunidadUsuario, Authentication authentication, Model model) {
	    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
	    Optional<Saldo> optionalSaldo = saldoRepository.findById(customUserDetails.getId());
	    if (optionalSaldo.isPresent()) {
	        Saldo saldo = optionalSaldo.get();
	        Double saldoActual = saldo.getSaldo();
	        if (oportunidadUsuario.getMonto_invertido() > saldoActual) {
	            String errorUrl = "/verOportunidad?idOportunidad=" + Id_Oportunidad + "&razonsocial=" + RazonSocial + "&error";
	            return "redirect:" + errorUrl;
	        }
	        double nuevoSaldo = saldoActual - oportunidadUsuario.getMonto_invertido();
	        saldo.setSaldo(nuevoSaldo);
	        saldoRepository.save(saldo);
	        Oportunidad oportunidad = oportunidadRepository.findByOportunidadId(Id_Oportunidad);
	        double nuevoMontoOportunidad = oportunidad.getMonto_disponible() - oportunidadUsuario.getMonto_invertido();
	        oportunidad.setMonto_disponible(nuevoMontoOportunidad);
	        
	        if (nuevoMontoOportunidad == 0) {
	            oportunidad.setEstado("Tomada");
	        }
	        oportunidadRepository.save(oportunidad);
	        oportunidadUsuario.setOportunidad_id(Id_Oportunidad);
	        oportunidadUsuario.setUsuario_id(customUserDetails.getId());
	        oportunidadUsuario.setFecha_registro(new Date());
	        oportunidadUsuarioRepository.save(oportunidadUsuario);
	        String exitoUrl = "/verOportunidad?idOportunidad=" + Id_Oportunidad + "&razonsocial=" + RazonSocial + "&exito";
	        return "redirect:" + exitoUrl;
	    }
	    String errorUrl = "/verOportunidad?idOportunidad=" + Id_Oportunidad + "&razonsocial=" + RazonSocial + "&error";
        return "redirect:" + errorUrl;
	}

}
