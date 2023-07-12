package com.creceperu.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.creceperu.app.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer>{
	boolean existsByRuc(String ruc);
    boolean existsByRazonsocial(String razonsocial);
    Empresa findByRazonsocial(String razonsocial);
}
