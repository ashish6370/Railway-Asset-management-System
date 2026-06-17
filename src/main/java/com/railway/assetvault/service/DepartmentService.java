package com.railway.assetvault.service;

import com.railway.assetvault.entity.Department;
import com.railway.assetvault.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.railway.assetvault.repository.DepotRepository;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository repository;

    @Autowired
    private DepotRepository depotRepository;

    public List<Department> getAll() {
        return repository.findAll();
    }

    public Department getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Department not found"));
    }

    public Department create(Department entity) {
        if (entity.getDepot() == null) {
            entity.setDepot(depotRepository.findAll().stream().findFirst().orElseThrow(() -> new RuntimeException("No Depot found")));
        }
        return repository.save(entity);
    }

    public Department update(Long id, Department details) {
        Department entity = getById(id);
        entity.setName(details.getName());
        entity.setCode(details.getCode());
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
