package com.arun.E_Commerce.Project.Service;

import com.arun.E_Commerce.Project.DAO.CategoryDAO;
import com.arun.E_Commerce.Project.Model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryDAO categoryDAO;

    public String createCategory(Category category){
        categoryDAO.save(category);
        return "Category Created";
    }

    public Category getCategoryById(int id){
        return categoryDAO.findById(id).orElse(null);
    }

    public List<Category> getAllCategories(){
        return categoryDAO.findAll();
    }

    public String updateCategory(Category category){
        categoryDAO.save(category);
        return "Category Updated";
    }
}
