package com.creceperu.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.creceperu.app.model.Saldo;

@Repository
public interface SaldoRepository extends JpaRepository<Saldo, Long>{

}
