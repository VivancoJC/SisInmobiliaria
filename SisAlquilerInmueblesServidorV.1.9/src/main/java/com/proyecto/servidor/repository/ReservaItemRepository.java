package com.proyecto.servidor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.servidor.model.ReservaItem;

public interface ReservaItemRepository extends JpaRepository<ReservaItem, Long> {
}