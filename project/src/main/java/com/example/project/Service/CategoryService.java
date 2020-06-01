package com.example.project.Service;

import com.example.project.Model.Category;
import com.example.project.Model.Vehicle;
import com.example.project.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> fetchAll() {
        return categoryRepository.fetchAll();
    }

    public Category addCategory(Category c) {
        return categoryRepository.addCategory(c);
    }

    public Category findCategory(int catId) {
        return categoryRepository.findCategory(catId);
    }

    public Boolean deleteCategory(int catId) {
        return categoryRepository.deleteCategory(catId);
    }

    public Category updateCategory(Category c) {
        return categoryRepository.updateCategory(c);
    }

    public List<Category> findByKeyword(String keyword)
    {
        return categoryRepository.findByKeyword(keyword);
    }

}
