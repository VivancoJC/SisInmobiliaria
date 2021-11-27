package com.proyecto.servidor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.servidor.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}