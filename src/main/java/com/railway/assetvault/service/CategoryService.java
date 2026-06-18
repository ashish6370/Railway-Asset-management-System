package com.railway.assetvault.service;

import com.railway.assetvault.dto.request.CategoryRequest;
import com.railway.assetvault.entity.Category;
import com.railway.assetvault.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    @Autowired private CategoryRepository repo;

    public List<Category> getAllCategories() { return repo.findAll(); }
    
    public Category createCategory(CategoryRequest req) {
        String name = req.getName() == null ? "" : req.getName().trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Category name is required");
        }
        if (repo.existsByNameIgnoreCase(name)) {
            throw new IllegalArgumentException("Category already exists");
        }

        Category c = new Category();
        c.setName(name);
        c.setDescription(req.getDescription() == null ? null : req.getDescription().trim());
        return repo.save(c);
    }
}
