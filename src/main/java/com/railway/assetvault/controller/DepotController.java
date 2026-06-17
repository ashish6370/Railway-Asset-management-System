package com.railway.assetvault.controller;

import com.railway.assetvault.entity.Depot;
import com.railway.assetvault.service.DepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/depots")
public class DepotController {

    @Autowired
    private DepotService service;

    @GetMapping
    public List<Depot> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Depot getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Depot create(@RequestBody Depot entity) {
        return service.create(entity);
    }

    @PutMapping("/{id}")
    public Depot update(@PathVariable Long id, @RequestBody Depot entity) {
        return service.update(id, entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
