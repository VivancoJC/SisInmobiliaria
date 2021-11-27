package com.proyecto.servidor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.servidor.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}