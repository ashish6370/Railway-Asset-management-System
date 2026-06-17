package com.railway.assetvault.controller;

import com.railway.assetvault.entity.Subcategory;
import com.railway.assetvault.entity.Category;
import com.railway.assetvault.repository.SubcategoryRepository;
import com.railway.assetvault.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/subcategories")
public class SubcategoryController {
    @Autowired private SubcategoryRepository repo;
    @Autowired private CategoryRepository catRepo;

    @GetMapping
    public List<Subcategory> getAll() { return repo.findAll(); }

    @PostMapping
    public Subcategory create(@RequestParam Long categoryId, @RequestParam String name) { 
        Subcategory s = new Subcategory();
        Category c = catRepo.findById(categoryId).orElseThrow();
        s.setCategory(c);
        s.setName(name);
        return repo.save(s); 
    }
}
