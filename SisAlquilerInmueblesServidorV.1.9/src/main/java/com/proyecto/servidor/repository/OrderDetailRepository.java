package com.proyecto.servidor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.servidor.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}