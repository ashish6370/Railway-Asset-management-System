package com.railway.assetvault.service;

import com.railway.assetvault.entity.Division;
import com.railway.assetvault.repository.DivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.railway.assetvault.repository.ZoneRepository;

import java.util.List;

@Service
public class DivisionService {

    @Autowired
    private DivisionRepository repository;

    @Autowired
    private ZoneRepository zoneRepository;

    public List<Division> getAll() {
        return repository.findAll();
    }

    public Division getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Division not found"));
    }

    public Division create(Division entity) {
        if (entity.getZone() == null) {
            entity.setZone(zoneRepository.findAll().stream().findFirst().orElseThrow(() -> new RuntimeException("No Zone found")));
        }
        return repository.save(entity);
    }

    public Division update(Long id, Division details) {
        Division entity = getById(id);
        entity.setName(details.getName());
        entity.setCode(details.getCode());
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
