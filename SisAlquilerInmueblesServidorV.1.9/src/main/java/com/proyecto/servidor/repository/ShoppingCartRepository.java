package com.proyecto.servidor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.servidor.model.VerificarCart;

public interface ShoppingCartRepository extends JpaRepository<VerificarCart, Long> {
}