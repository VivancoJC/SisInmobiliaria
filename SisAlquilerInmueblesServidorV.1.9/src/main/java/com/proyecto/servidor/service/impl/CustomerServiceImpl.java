package com.proyecto.servidor.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.servidor.model.Country;
import com.proyecto.servidor.model.Customer;
import com.proyecto.servidor.model.Role;
import com.proyecto.servidor.repository.CountryRepository;
import com.proyecto.servidor.repository.CustomerRepository;
import com.proyecto.servidor.repository.RoleRepository;
import com.proyecto.servidor.service.CustomerService;
import com.proyecto.servidor.web.dto.CustomerRegistrationDto;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    //  private CustomerRepository customerDAO;--cambiar  repository x  dao, esto apunta arepositori/dao
    private CustomerRepository customerRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
 


    public CustomerServiceImpl() {
        super();
    }

    @Override
    public Customer save(CustomerRegistrationDto registrationDto) {

        //Creating admin role user
        Customer customer = new Customer();
        customer.setFirstName(registrationDto.getFirstName());
        customer.setLastName(registrationDto.getLastName());
        customer.setUsername(registrationDto.getUsername());
        customer.setPhone(registrationDto.getPhone());
        customer.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        customer.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
        customer.setIsDeleted(false);

        //Get country
        Country country = countryRepository.findByName(registrationDto.getCountry());

        //Address
        customer.setCompany(registrationDto.getCompany());
        customer.setAddress1(registrationDto.getAddress1());
        customer.setAddress2(registrationDto.getAddress2());
    
        customer.setCity(registrationDto.getCity());
        customer.setCountry(country);
    

        return customerRepository.save(customer);
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = customerRepository.findByUsername(username);
        if(customer == null) {
            throw new UsernameNotFoundException("Usuario o contraseña invalido");
        }
        return new org.springframework.security.core.userdetails.User(customer.getUsername(), customer.getPassword()
                , mapRolesToAuthorities(customer.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
