package com.proyecto.servidor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.servidor.model.ReservaItem;
import com.proyecto.servidor.repository.ReservaItemRepository;

@Service
@Transactional
public class ReservaItemService {

    @Autowired
    private ReservaItemRepository cartItemRepository;

    public List<ReservaItem> findAll() {
        return (List<ReservaItem>) cartItemRepository.findAll();
    }

    public void save(ReservaItem order) {
        cartItemRepository.save(order);
    }

    public ReservaItem get(long id) {
        return cartItemRepository.findById(id).get();
    }

    public void delete(long id) {
        cartItemRepository.deleteById(id);
    }
}