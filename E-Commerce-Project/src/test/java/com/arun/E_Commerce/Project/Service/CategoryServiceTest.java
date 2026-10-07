package com.arun.E_Commerce.Project.Service;

import com.arun.E_Commerce.Project.DAO.CategoryDAO;
import com.arun.E_Commerce.Project.Model.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    private CategoryDAO categoryDAO;
    private static Category category = null;
    @InjectMocks
    CategoryService categoryService;

    @BeforeAll
    static void demoCategory(){
        category = new Category();
        category.setName("Toys");
        category.setId(2);
        category.setDescription("It's A Toy");
    }
    @Test
    void createCategoryTest() {
        String result = categoryService.createCategory(category);
        assertEquals("Category Created", result);
        verify(categoryDAO).save(category);
    }

    @Test
    void getCategoryByIdTest() {
        when(categoryDAO.findById(2)).thenReturn(Optional.of(category));
        Category result = categoryService.getCategoryById(2);
        assertEquals(category, result);
        verify(categoryDAO).findById(2);
    }

    @Test
    void categoryNotFoundTest(){
        when(categoryDAO.findById(4)).thenReturn(Optional.empty());
        Category result = categoryService.getCategoryById(4);
        assertNull(result);
    }

    @Test
    void getAllCategoriesTest() {
        when(categoryDAO.findAll()).thenReturn(List.of(category));
        List<Category> result = categoryService.getAllCategories();
        assertEquals(List.of(category), result);
        verify(categoryDAO).findAll();
    }

    @Test
    void updateCategoryTest() {
        when(categoryDAO.findById(2)).thenReturn(Optional.of(category));
        String result = categoryService.updateCategory(category, 2);
        assertEquals("Category Is Updated", result);
        verify(categoryDAO).save(category);
    }

    @Test
    void deleteCategoryTest() {
        when(categoryDAO.findById(2)).thenReturn(Optional.of(category));
        String result = categoryService.deleteCategory(2);
        assertEquals("Category Is Deleted", result);
        verify(categoryDAO).delete(category);
    }
}