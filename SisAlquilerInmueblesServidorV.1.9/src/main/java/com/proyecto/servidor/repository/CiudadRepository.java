package com.proyecto.servidor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.servidor.model.Ciudad;

public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
    Ciudad findByName(String name);
}