package com.proyecto.servidor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.servidor.model.Contrato;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    Contrato findByName(String name);


    @Query("select m from Contrato m where 1=1 and is_deleted=0 and name=?1 and make_id=?2")
    public Contrato findByName2(String name, Long makeId);

    @Query("select m from Contrato m where 1=1 and is_deleted=0 and (model_id=1 or make_id=?1)")
    public List<Contrato> getModels(Long makeId);

}