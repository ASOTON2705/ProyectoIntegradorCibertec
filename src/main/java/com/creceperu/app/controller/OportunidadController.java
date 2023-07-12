package com.creceperu.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creceperu.app.controller.dto.OportunidadDTO;
import com.creceperu.app.model.Oportunidad;
import com.creceperu.app.model.OportunidadUsuario;
import com.creceperu.app.model.OportunidadesXFiltroResult;
import com.creceperu.app.repository.EmpresaRepository;
import com.creceperu.app.repository.FacturaRepository;
import com.creceperu.app.repository.OportunidadFacturaRepository;
import com.creceperu.app.repository.OportunidadRepository;
import com.creceperu.app.repository.OportunidadUsuarioRepository;
import com.creceperu.app.service.OportunidadService;
import com.creceperu.app.service.UsuarioServiceImpl.CustomUserDetails;

@Controller
@RequestMapping("/oportunidad")
public class OportunidadController {
	
	@Autowired
	private OportunidadService oportunidadService;
	
	@Autowired
	private OportunidadRepository oportunidadRepository;
	
	@Autowired
	private OportunidadFacturaRepository oportunidadFacturaRepository;
	
	@Autowired
	private OportunidadUsuarioRepository oportunidadUsuarioRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private FacturaRepository facturaRepository;
	
	public OportunidadController (OportunidadService oportunidadService) {
		super();
		this.oportunidadService = oportunidadService;
	}
	
	@ModelAttribute("oportunidad")
	public OportunidadDTO oportunidadDTO() {
		return new OportunidadDTO();
	}
	
	@GetMapping("/registroOportunidad")
	public String mostrarRegistroDeOportunidad(Model model) {
		model.addAttribute("lstEmpresa", empresaRepository.findAll());
		return "oportunidad";
	}
	
	@GetMapping("/facturas")
    @ResponseBody
    public List<Object[]> getFacturaDataByRuc(@RequestParam("id_empresa") Integer id_empresa) {
        return facturaRepository.findFacturaDataById_empresa(id_empresa);
    }
   
    @PostMapping("/registrarOportunidad")
    @Transactional
    public String registrarOportunidad(@RequestParam("codigos") String codigos,
    		@ModelAttribute("oportunidad") OportunidadDTO oportunidadDTO) {
    	if (codigos == null || codigos.isEmpty()) {
            return "redirect:/oportunidad/registroOportunidad?error";
        }
    	String lastCode = oportunidadRepository.getLastGeneratedCode();
    	String nextCode = generateNextCode(lastCode);
    	oportunidadDTO.setId_oportunidad(nextCode);
    	oportunidadDTO.setFecharegistro(new Date());
    	oportunidadDTO.setEstado("Disponible");
    	oportunidadDTO.setMonto_disponible(oportunidadDTO.getMonto());
        oportunidadService.guardar(oportunidadDTO);
        String[] codigoArray = codigos.split(",");
        for (String codigo : codigoArray) {
            oportunidadFacturaRepository.insertOportunidadFactura(nextCode, codigo);
        }
        List<String> codigosList = Arrays.asList(codigoArray);
        facturaRepository.actualizarEstadoFacturas(codigosList);
    	return "redirect:/oportunidad/registroOportunidad?exito";
    }
    
    private String generateNextCode(String lastCode) {
        if (lastCode == null) {
            return "O000001";
        }
        int lastNumber = Integer.parseInt(lastCode.substring(1));
        int nextNumber = lastNumber + 1;
        String nextNumberWithPadding = String.format("%06d", nextNumber);
        return "O" + nextNumberWithPadding;
    }
    
    @GetMapping("/listaDeOportunidades")
    public String listaDeOportunidades(Model model, @RequestParam(required = false, defaultValue = "") String razonsocial,
            @RequestParam(required = false, defaultValue = "") String ruc, @RequestParam(required = false, defaultValue = "") String fecharegistro,
            @RequestParam(required = false, defaultValue = "") String fechaPago, @RequestParam(required = false, defaultValue = "") String calificacion,
            @RequestParam(required = false, defaultValue = "") String estado, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 10; // Número de elementos por página
        Pageable pageable = PageRequest.of(page, pageSize);
        Date fechaRegistroStr = null;
        Date fechaPagoStr = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        try {
            if (!fecharegistro.isEmpty()) {
                fechaRegistroStr = dateFormat.parse(fecharegistro);
            }
            if (!fechaPago.isEmpty()) {
                fechaPagoStr = dateFormat.parse(fechaPago);
            }
            if (razonsocial.isEmpty()) {
                razonsocial = null;
            }
            if (ruc.isEmpty()) {
                ruc = null;
            }
            if (calificacion.isEmpty()) {
                calificacion = null;
            }
            if (estado.isEmpty()) {
                estado = null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Page<Oportunidad> oportunidadXFiltroPage = oportunidadRepository.findOportunidadesByFiltro(razonsocial, ruc, fechaRegistroStr, fechaPagoStr, calificacion, estado, pageable);
        model.addAttribute("lstOportunidades", oportunidadXFiltroPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", oportunidadXFiltroPage.getTotalPages());
        // Agregar los parámetros de búsqueda al modelo para mantenerlos en la vista
        model.addAttribute("razonsocial", razonsocial);
        model.addAttribute("ruc", ruc);
        model.addAttribute("fecharegistro", fecharegistro);
        model.addAttribute("fechaPago", fechaPago);
        model.addAttribute("calificacion", calificacion);
        model.addAttribute("estado", estado);
        return "listaOportunidades";
    }

    @GetMapping("/listaInversionesXOportunidad")
	public String listaDeInversionesXOportunidad(Model model, @RequestParam("idOportunidad") String idOportunidad,@RequestParam(defaultValue = "0") int page) {
    	int pageSize = 3; // Número de elementos por página
    	Oportunidad oportunidad = oportunidadRepository.findByOportunidadId(idOportunidad);
    	Long conteoInversiones = oportunidadUsuarioRepository.contarInversionesPorOportunidadId(idOportunidad);
		model.addAttribute("conteoInversiones", conteoInversiones);
	    model.addAttribute("oportunidadXId", oportunidad);
	    Sort sort = Sort.by(Sort.Direction.DESC, "fecha_registro");
	    Pageable pageable = PageRequest.of(page, pageSize, sort);
	    Page<OportunidadUsuario> oportunidadUsuarioPage = oportunidadUsuarioRepository.findfindByOportunidad_id(idOportunidad, pageable);
	    model.addAttribute("lstOportunidadesUsuario", oportunidadUsuarioPage.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", oportunidadUsuarioPage.getTotalPages());
	    model.addAttribute("idOportunidad", idOportunidad);
	    return "oportunidadDetalle";
	}
}
