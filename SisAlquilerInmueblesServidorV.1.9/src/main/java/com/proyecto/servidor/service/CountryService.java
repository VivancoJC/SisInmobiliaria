package com.proyecto.servidor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.servidor.model.Country;
import com.proyecto.servidor.repository.CountryRepository;

@Service
@Transactional
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<Country> findAll() {
        return (List<Country>) countryRepository.findAll();
    }

    public Country findByName(String name) {
        return countryRepository.findByName(name);
    }

    public void save(Country product) {
        countryRepository.save(product);
    }

    public Country get(long  id) {
        return countryRepository.findById(id).get();
    }

    public void delete(long id) {
        countryRepository.deleteById(id);
    }
}