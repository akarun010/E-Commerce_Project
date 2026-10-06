package com.arun.E_Commerce.Project.Controller;

import com.arun.E_Commerce.Project.Model.Category;
import com.arun.E_Commerce.Project.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/category")
    public String createCategory(@RequestBody @Valid Category category){
        return categoryService.createCategory(category);
    }

    @GetMapping("/category")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

   @GetMapping("/category/{categoryId}")
   public Category getCategoryById(@PathVariable int categoryId){
       return categoryService.getCategoryById(categoryId);
   }

   @PutMapping("/category/{categoryId}")
    public String updateCategory(@RequestBody @Valid Category category, @PathVariable int categoryId){
        return categoryService.updateCategory(category,categoryId);
    };

    @DeleteMapping("/category/{categoryId}")
    public String deleteCategory(@PathVariable int categoryId){
        return categoryService.deleteCategory(categoryId);
    };
}
