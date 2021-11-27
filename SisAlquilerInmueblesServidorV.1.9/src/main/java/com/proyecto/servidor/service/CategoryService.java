package com.proyecto.servidor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.servidor.model.Category;
import com.proyecto.servidor.repository.CategoryRepository;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return (List<Category>) categoryRepository.findAll();
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public void save(Category product) {
        categoryRepository.save(product);
    }

    public Category get(long  id) {
        return categoryRepository.findById(id).get();
    }

    public void delete(long id) {
        categoryRepository.deleteById(id);
    }
}