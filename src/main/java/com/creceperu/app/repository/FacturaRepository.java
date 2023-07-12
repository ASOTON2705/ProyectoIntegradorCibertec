package com.creceperu.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.creceperu.app.model.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura,String>{
	@Query(value = "SELECT id_factura FROM Factura ORDER BY id_factura DESC LIMIT 1", nativeQuery = true)
    String getLastGeneratedCode();
	List<Factura> findByRuc(String ruc);
	@Query("SELECT f.id_factura, DATE_FORMAT(f.fecharegistro, '%Y-%m-%d %H:%i:%s'), DATE_FORMAT(f.fechavencimiento, '%Y-%m-%d %H:%i:%s'), f.monto FROM Factura f inner join Empresa e on f.ruc=e.ruc WHERE f.estado='Listada' and e.id_empresa = :id_empresa")
    List<Object[]> findFacturaDataById_empresa(@Param("id_empresa") Integer id_empresa);
    @Query("SELECT f FROM Factura f WHERE f.id_factura = ?1")
    Factura findById_factura(String id_factura);
    @Modifying
    @Query("UPDATE Factura f SET f.estado = 'Activa' WHERE f.id_factura IN :codigos")
    void actualizarEstadoFacturas(@Param("codigos") List<String> codigos);
}
