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
        Category c = new Category();
        c.setName(req.getName());
        c.setDescription(req.getDescription());
        return repo.save(c);
    }
}
