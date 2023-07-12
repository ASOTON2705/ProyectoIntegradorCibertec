package com.creceperu.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.creceperu.app.model.Rol;
@Repository
public interface RolRepository extends JpaRepository<Rol, Long>{
	public Rol findByNombre(String nombre);
}
