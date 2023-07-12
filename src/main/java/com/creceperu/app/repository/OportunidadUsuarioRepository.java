package com.creceperu.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.creceperu.app.model.OportunidadUsuario;

@Repository
public interface OportunidadUsuarioRepository extends JpaRepository<OportunidadUsuario, String>{
	List<OportunidadUsuario> findByObjUsuarioId(Long id);
	Page<OportunidadUsuario> findByObjUsuarioId(Long id, Pageable pageable);
	@Query(value = "SELECT COUNT(ou.usuario_id) AS numerooportunidadesinvertidas , COALESCE(SUM(ou.monto_invertido), 0) AS montototalinvertido FROM oportunidad_usuario ou WHERE ou.usuario_id = :usuarioId", nativeQuery = true)
	List<Object[]> getOportunidadesUsuario(@Param("usuarioId") Long usuarioId);
	@Query("SELECT ou FROM OportunidadUsuario ou WHERE ou.oportunidad_id = :idOportunidad ORDER BY ou.fecha_registro DESC")
	List<OportunidadUsuario> findUltimasInversiones(@Param("idOportunidad") String idOportunidad, Pageable pageable);
	@Query("SELECT COUNT(ou) FROM OportunidadUsuario ou WHERE ou.oportunidad_id = :idOportunidad")
	Long contarInversionesPorOportunidadId(@Param("idOportunidad") String idOportunidad);
	@Query("SELECT ou FROM OportunidadUsuario ou WHERE ou.oportunidad_id = :idOportunidad ORDER BY ou.fecha_registro DESC")
	Page<OportunidadUsuario> findfindByOportunidad_id(@Param("idOportunidad") String idOportunidad, Pageable pageable);
}
