/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.openmarket.domain.service;

import co.edu.unicauca.openmarket.access.ICategoryRepository;
import co.edu.unicauca.openmarket.domain.Category;

import java.util.List;

/**
 *
 * @author Valentina Fernández y Andres Collazos
 */
public class ProductCategory {
    
    private final ICategoryRepository categoryRepository;
    
    public ProductCategory(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    public boolean saveCategory(String name) {
        Category newCategory = new Category();
        newCategory.setName(name);

        // Validar categoría
        if (newCategory.getName().isEmpty()) {
            return false;
        }

        return categoryRepository.save(newCategory);
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public boolean editCategory(Long categoryId, Category category) {
        // Validar categoría
        if (category == null || category.getName().isEmpty()) {
            return false;
        }
        return categoryRepository.edit(categoryId, category);
    }

    public boolean deleteCategory(Long id) {
        return categoryRepository.delete(id);
    }

    public Category findCategoryByName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        return categoryRepository.findByName(name);
    }
}
