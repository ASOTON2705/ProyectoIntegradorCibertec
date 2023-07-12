package com.creceperu.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.creceperu.app.model.Banco;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Integer>{

}
