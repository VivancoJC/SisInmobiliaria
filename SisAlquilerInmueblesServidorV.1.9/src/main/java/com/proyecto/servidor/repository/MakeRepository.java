package com.proyecto.servidor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.servidor.model.Empresa;

public interface MakeRepository extends JpaRepository<Empresa, Long> {
    Empresa findByName(String name);
}