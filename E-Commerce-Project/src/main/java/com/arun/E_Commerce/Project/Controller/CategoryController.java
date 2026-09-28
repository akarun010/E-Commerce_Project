package com.arun.E_Commerce.Project.Controller;

import com.arun.E_Commerce.Project.Model.Category;
import com.arun.E_Commerce.Project.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/category")
    public String createCategory(@RequestBody Category category){
        return categoryService.createCategory(category);
    }

    @GetMapping("/category")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

   @GetMapping("/category/{categoryId}")
   public Category getAllCategories(@PathVariable int categoryId){
       return categoryService.getCategoryById(categoryId);
   }
}
