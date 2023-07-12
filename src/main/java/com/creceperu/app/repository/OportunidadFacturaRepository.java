package com.creceperu.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.creceperu.app.model.OportunidadFactura;

@Repository
public interface OportunidadFacturaRepository extends JpaRepository<OportunidadFactura, String>{
	 @Modifying
	 @Query(value = "INSERT INTO oportunidad_factura (oportunidad_id, factura_id) VALUES (:oportunidadId, :facturaId)", nativeQuery = true)
	 void insertOportunidadFactura(@Param("oportunidadId") String oportunidadId, @Param("facturaId") String facturaId);
}
