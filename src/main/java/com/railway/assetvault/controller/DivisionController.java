package com.railway.assetvault.controller;

import com.railway.assetvault.entity.Division;
import com.railway.assetvault.service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/divisions")
public class DivisionController {

    @Autowired
    private DivisionService service;

    @GetMapping
    public List<Division> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Division getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Division create(@RequestBody Division entity) {
        return service.create(entity);
    }

    @PutMapping("/{id}")
    public Division update(@PathVariable Long id, @RequestBody Division entity) {
        return service.update(id, entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
