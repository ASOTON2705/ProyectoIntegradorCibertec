package com.creceperu.app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.creceperu.app.model.Oportunidad;
import com.creceperu.app.model.OportunidadesXFiltroResult;

@Repository
public interface OportunidadRepository extends JpaRepository<Oportunidad, String>{
	@Query(value = "SELECT id_oportunidad FROM Oportunidad ORDER BY id_oportunidad DESC LIMIT 1", nativeQuery = true)
	String getLastGeneratedCode();
	@Query(value = "SELECT * FROM oportunidad WHERE estado = 'Disponible' AND fecha_pago > CURRENT_DATE() ORDER BY o.fechaPago", nativeQuery = true)
	Page<Oportunidad> findAllOportunidades(Pageable pageable);
	@Query("SELECT o FROM Oportunidad o INNER JOIN o.objEmpresa e WHERE o.estado = 'Disponible' AND ((:filtro = '') OR (o.calificacion = :filtro OR e.razonsocial = :filtro)) AND o.fechaPago > CURRENT_DATE() ORDER BY o.fechaPago")
	Page<Oportunidad> findOportunidadesXFiltro(@Param("filtro") String filtro,Pageable pageable);

	@Query("SELECT o FROM Oportunidad o WHERE o.id_oportunidad = :idOportunidad")
    Oportunidad findByOportunidadId(@Param("idOportunidad") String idOportunidad);
	@Query(value = "SELECT COUNT(o.estado) AS oportunidadesdisponibles, COALESCE(SUM(o.monto),0) AS montooportunidadesdisponibles FROM empresa e INNER JOIN oportunidad o ON e.id_empresa = o.id_empresa WHERE o.fecha_pago > CURDATE() AND o.estado = 'Disponible' AND e.razonsocial = :razonsocial", nativeQuery = true)
	List<Object[]> getOportunidadesDisponibles(@Param("razonsocial") String razonsocial);
	@Query(value = "SELECT COUNT(o.estado) AS oportunidadesretrasadas, COALESCE(SUM(o.monto),0) AS montooportunidadesretrasadas FROM empresa e INNER JOIN oportunidad o ON e.id_empresa = o.id_empresa WHERE o.fecha_pago < CURDATE() AND e.razonsocial = :razonsocial", nativeQuery = true)
	List<Object[]> getOportunidadesRetrasadas(@Param("razonsocial") String razonsocial);
	@Query(value = "SELECT COUNT(o.estado) AS oportunidadespagadas, COALESCE(SUM(o.monto),0) AS montooportunidadespagadas FROM empresa e INNER JOIN oportunidad o ON e.id_empresa = o.id_empresa WHERE o.estado = 'Pagada' AND e.razonsocial = :razonsocial", nativeQuery = true)
	List<Object[]> getOportunidadesPagadas(@Param("razonsocial") String razonsocial);
	
	@Query("SELECT o FROM Oportunidad o INNER JOIN o.objEmpresa e " +
            "WHERE (:razonsocial IS NULL OR e.razonsocial = :razonsocial) " +
            "AND (:ruc IS NULL OR e.ruc = :ruc) " +
            "AND (:fecharegistro IS NULL OR DATE(o.fecharegistro) = :fecharegistro) " +
            "AND (:fechaPago IS NULL OR DATE(o.fechaPago) = :fechaPago) " +
            "AND (:calificacion IS NULL OR o.calificacion = :calificacion) " +
            "AND (:estado IS NULL OR o.estado = :estado)" +
            "ORDER BY o.fecharegistro")
	Page<Oportunidad> findOportunidadesByFiltro(@Param("razonsocial") String razonsocial,
                                                @Param("ruc") String ruc,
                                                @Param("fecharegistro") Date fecharegistro,
                                                @Param("fechaPago") Date fechaPago,
                                                @Param("calificacion") String calificacion,
                                                @Param("estado") String estado,
                                                Pageable pageable);
}
