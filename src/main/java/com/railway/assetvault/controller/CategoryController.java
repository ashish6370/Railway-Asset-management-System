package com.railway.assetvault.controller;

import com.railway.assetvault.dto.request.CategoryRequest;
import com.railway.assetvault.entity.Category;
import com.railway.assetvault.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired private CategoryService service;

    @GetMapping
    public List<Category> getAll() { return service.getAllCategories(); }

    @PostMapping
    public Category create(@RequestBody CategoryRequest req) { return service.createCategory(req); }
}
