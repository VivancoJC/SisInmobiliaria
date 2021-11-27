package com.proyecto.servidor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.servidor.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}