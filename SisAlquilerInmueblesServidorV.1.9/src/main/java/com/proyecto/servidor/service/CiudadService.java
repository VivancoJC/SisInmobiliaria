package com.proyecto.servidor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.servidor.model.Ciudad;
import com.proyecto.servidor.repository.CiudadRepository;

@Service
@Transactional
public class CiudadService {

    @Autowired
    private CiudadRepository countryRepository;

    public List<Ciudad> findAll() {
        return (List<Ciudad>) countryRepository.findAll();
    }

    public Ciudad findByName(String name) {
        return countryRepository.findByName(name);
    }

    public void save(Ciudad product) {
        countryRepository.save(product);
    }

    public Ciudad get(long  id) {
        return countryRepository.findById(id).get();
    }

    public void delete(long id) {
        countryRepository.deleteById(id);
    }
}