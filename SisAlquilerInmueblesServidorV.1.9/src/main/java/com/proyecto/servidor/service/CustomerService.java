package com.proyecto.servidor.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.proyecto.servidor.model.Customer;
import com.proyecto.servidor.web.dto.CustomerRegistrationDto;

public interface CustomerService extends UserDetailsService{
    Customer save(CustomerRegistrationDto registrationDto);//For insert
    Customer save(Customer customer);//For update
    Customer findByUsername(String username);
}
