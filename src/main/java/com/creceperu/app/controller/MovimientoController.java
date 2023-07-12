package com.creceperu.app.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.creceperu.app.controller.dto.MovimientoRegistroDTO;
import com.creceperu.app.model.CuentaBancaria;
import com.creceperu.app.model.Movimiento;
import com.creceperu.app.model.Saldo;
import com.creceperu.app.model.Usuario;
import com.creceperu.app.repository.CuentaBancariaRepository;
import com.creceperu.app.repository.MovimientoRepository;
import com.creceperu.app.repository.SaldoRepository;
import com.creceperu.app.repository.UsuarioRepository;
import com.creceperu.app.service.MovimientoService;
import com.creceperu.app.service.UsuarioServiceImpl.CustomUserDetails;

@Controller
@RequestMapping("/registroMovimiento")
public class MovimientoController {
	
	@Autowired
	private MovimientoService movimientoService;
	
	@Autowired
	private CuentaBancariaRepository cuentaBancariaRepository;
	
	@Autowired
	private MovimientoRepository movimientoRepository;
	
	@Autowired
	private SaldoRepository saldoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public MovimientoController(MovimientoService movimientoService) {
		super();
		this.movimientoService = movimientoService;
	}
	
	@ModelAttribute("movimiento")
	public MovimientoRegistroDTO retornarNuevoMovimientoDTO() {
		return new MovimientoRegistroDTO();
	}
	
	@GetMapping("/movimientos")
	public String mostrarListaDeMovimientos(Model model, Authentication authentication, @RequestParam(defaultValue = "0") int page) {
	    int pageSize = 5; // Número de elementos por página
	    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
	    Sort sort = Sort.by(Sort.Direction.DESC, "fechaMovimiento");
	    Pageable pageable = PageRequest.of(page, pageSize, sort);
	    Page<Movimiento> movimientoPage = movimientoRepository.findByObjUsuarioId(customUserDetails.getId(), pageable);
	    model.addAttribute("lstMovimientos", movimientoPage.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", movimientoPage.getTotalPages());
	    return "movimientos";
	}
	
	@GetMapping("/deposito")
	public String mostrarFormularioDeRegistroMovimientoDeposito(Model model, Authentication authentication) {
		
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
	    List<CuentaBancaria> cuentasBancarias = cuentaBancariaRepository.findByObjUsuarioId(customUserDetails.getId());
	    model.addAttribute("lstCuentasBancarias", cuentasBancarias);
	    return "movimientoDeposito";
	}
	@GetMapping("/retiro")
	public String mostrarFormularioDeRegistroMovimientoRetiro(Model model, Authentication authentication) {
		
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
	    List<CuentaBancaria> cuentasBancarias = cuentaBancariaRepository.findByObjUsuarioId(customUserDetails.getId());
	    model.addAttribute("lstCuentasBancarias", cuentasBancarias);
	    return "movimientoRetiro";
	}
	
	@PostMapping("/deposito")
	public String registrarMovimientoDeposito(@RequestParam("idUsuario") String idUsuario, @ModelAttribute("movimiento") MovimientoRegistroDTO movimientoRegistroDTO) {
		Long idusuario = Long.parseLong(idUsuario);
		movimientoRegistroDTO.setId(idusuario);
		movimientoRegistroDTO.setTipoMovimiento("Deposito");
		movimientoRegistroDTO.setFechaMovimiento(new Date());
		
		Integer idCuenta = movimientoRegistroDTO.getId_cuentaBancaria();
		Optional<CuentaBancaria> optionalCuenta = cuentaBancariaRepository.findById(idCuenta);
		if(optionalCuenta.isPresent()) {
			CuentaBancaria cuentaBancaria = optionalCuenta.get();
			Double fondo = cuentaBancaria.getMonto();
			if (movimientoRegistroDTO.getMonto() > fondo) {
				return "redirect:/registroMovimiento/deposito?error";
			}
			Double nuevoFondo = fondo - movimientoRegistroDTO.getMonto();
			cuentaBancaria.setMonto(nuevoFondo);
			cuentaBancariaRepository.save(cuentaBancaria);
			movimientoService.guardar(movimientoRegistroDTO);
		    Usuario usuario = usuarioRepository.findById(idusuario).get();
		    Saldo saldo = usuario.getObjSaldo();
		    Double nuevoSaldo = saldo.getSaldo() + movimientoRegistroDTO.getMonto();
		    saldo.setSaldo(nuevoSaldo);
		    saldoRepository.save(saldo);
		    return "redirect:/registroMovimiento/deposito?exito";
		}

		return "redirect:/registroMovimiento/deposito?error";
	}
	
	@PostMapping("/retiro")
	@Transactional
	public String registrarMovimientoRetiro(@RequestParam("idUsuario") String idUsuario, @ModelAttribute("movimiento") MovimientoRegistroDTO movimientoRegistroDTO) {
	    Long idusuario = Long.parseLong(idUsuario);
	    movimientoRegistroDTO.setId(idusuario);
	    movimientoRegistroDTO.setTipoMovimiento("Retiro");
	    movimientoRegistroDTO.setFechaMovimiento(new Date());

	    Optional<Saldo> optionalSaldo = saldoRepository.findById(idusuario);
	    if (optionalSaldo.isPresent()) {
	        Saldo saldo = optionalSaldo.get();
	        Double saldoActual = saldo.getSaldo();

	        if (movimientoRegistroDTO.getMonto() > saldoActual) {
	            return "redirect:/registroMovimiento/retiro?error";
	        }

	        Double nuevoSaldo = saldoActual - movimientoRegistroDTO.getMonto();
	        saldo.setSaldo(nuevoSaldo);
	        saldoRepository.save(saldo);
	        cuentaBancariaRepository.actualizarMontoCuentaBancaria(movimientoRegistroDTO.getId_cuentaBancaria(), movimientoRegistroDTO.getMonto());
	        movimientoService.guardar(movimientoRegistroDTO);
	        return "redirect:/registroMovimiento/retiro?exito";
	    }

	    return "redirect:/registroMovimiento/retiro?error";
	}
}
