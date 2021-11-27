package com.proyecto.servidor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.servidor.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByName(String name);
}