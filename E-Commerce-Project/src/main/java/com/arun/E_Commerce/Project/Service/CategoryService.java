package com.arun.E_Commerce.Project.Service;

import com.arun.E_Commerce.Project.DAO.CategoryDAO;
import com.arun.E_Commerce.Project.Model.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryService {
    @Autowired
    private CategoryDAO categoryDAO;

    public String createCategory(Category category){
        categoryDAO.save(category);
        log.info("Category {} Created", category.getId());
        return "Category Created";
    }

    public Category getCategoryById(int id){
        Category category = categoryDAO.findById(id).orElse(null);
        if(category == null){
            log.warn("Category {} Not Found", id);
            return null;
        }
        log.info("Category {} Is Accessed", id);
        return category;
    }

    public List<Category> getAllCategories(){
        log.info("All Categories Are Accessed");
        return categoryDAO.findAll();
    }

    public String updateCategory(Category category, int id){
        Category existingCategory = categoryDAO.findById(id).orElse(null);
        if(existingCategory != null){
            existingCategory.setDescription(category.getDescription());
            existingCategory.setName(category.getName());
            categoryDAO.save(existingCategory);
            log.info("Category {} Is Updated", id);
            return "Category Is Updated";
        }
        log.warn("Category {} Not Found", id);
        return "Category Is Not Found";
    }

    public String deleteCategory(int id){
        Category existingCategory = categoryDAO.findById(id).orElse(null);
        if(existingCategory != null){
            categoryDAO.delete(existingCategory);
            log.info("Category {} Is Deleted", id);
            return "Category Is Deleted";
        }
        log.warn("Category {} Not Found", id);
        return "Category Is Not Found";
    }
}
