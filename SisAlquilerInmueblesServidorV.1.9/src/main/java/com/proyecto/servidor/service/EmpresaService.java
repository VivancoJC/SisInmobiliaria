package com.proyecto.servidor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.servidor.model.Empresa;
import com.proyecto.servidor.repository.EmpresaRepository;

@Service
@Transactional
public class EmpresaService {

    @Autowired
    private EmpresaRepository makeRepository;

    public List<Empresa> findAll() {
        return (List<Empresa>) makeRepository.findAll();
    }

    public Empresa findByName(String name) {
        return makeRepository.findByName(name);
    }

    public void save(Empresa product) {
        makeRepository.save(product);
    }

    public Empresa get(long  id) {
        return makeRepository.findById(id).get();
    }

    public void delete(long id) {
        makeRepository.deleteById(id);
    }
}