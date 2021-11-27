package com.proyecto.servidor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.servidor.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}