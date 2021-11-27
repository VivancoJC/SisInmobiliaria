package com.proyecto.servidor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.servidor.model.Contrato;
import com.proyecto.servidor.repository.ModelRepository;

@Service
@Transactional
public class ModelService {

    @Autowired
    private ModelRepository modelRepository;

    public List<Contrato> findAll() {
        return (List<Contrato>) modelRepository.findAll();
    }

    public Contrato findByName(String name) {
        return modelRepository.findByName(name);
    }

    public List<Contrato> getModels(Long makeId) {
        return modelRepository.getModels(makeId);
    }



    public void save(Contrato product) {
        modelRepository.save(product);
    }

    public Contrato get(long  id) {
        return modelRepository.findById(id).get();
    }

    public void delete(long id) {
        modelRepository.deleteById(id);
    }
}