package com.railway.assetvault.service;

import com.railway.assetvault.entity.Depot;
import com.railway.assetvault.repository.DepotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.railway.assetvault.repository.DivisionRepository;

import java.util.List;

@Service
public class DepotService {

    @Autowired
    private DepotRepository repository;

    @Autowired
    private DivisionRepository divisionRepository;

    public List<Depot> getAll() {
        return repository.findAll();
    }

    public Depot getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Depot not found"));
    }

    public Depot create(Depot entity) {
        if (entity.getDivision() == null) {
            entity.setDivision(divisionRepository.findAll().stream().findFirst().orElseThrow(() -> new RuntimeException("No Division found")));
        }
        return repository.save(entity);
    }

    public Depot update(Long id, Depot details) {
        Depot entity = getById(id);
        entity.setName(details.getName());
        entity.setCode(details.getCode());
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
