package com.creceperu.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.creceperu.app.model.Movimiento;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Integer>{
	Page<Movimiento> findByObjUsuarioId(Long id, Pageable pageable);
}
