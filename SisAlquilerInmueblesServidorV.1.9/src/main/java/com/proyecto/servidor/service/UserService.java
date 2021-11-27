package com.proyecto.servidor.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.proyecto.servidor.model.User;
import com.proyecto.servidor.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
    User save(UserRegistrationDto registrationDto);
    User findByUsername(String username);
}
