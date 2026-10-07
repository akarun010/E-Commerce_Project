package com.arun.E_Commerce.Project.Service;

import com.arun.E_Commerce.Project.DAO.ProductDAO;
import com.arun.E_Commerce.Project.Model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    ProductDAO productDAO;
    @InjectMocks
    ProductService productService;

    static Product product = null;

    @BeforeAll
    public static void demoProduct(){
        product = new Product();
        product.setId(3);
        product.setCreatedAt(LocalDate.now());
        product.setPrice(new BigDecimal("5000.00"));
        product.setQuantity(20);
        product.setDescription("A Beautiful Toy");
        product.setName("Legos");
    }

    @Test
    void createProductTest() {
        String result = productService.createProduct(product);
        assertEquals("Product Created", result);
        verify(productDAO).save(product);
    }

    @Test
    void getProductByIdTest() {
        when(productDAO.findById(3)).thenReturn(Optional.of(product));
        Product result = productService.getProductById(3);
        assertEquals(product.getId(), result.getId());
    }

    @Test
    void productNotFoundTest() {
        when(productDAO.findById(99)).thenReturn(Optional.empty());
        Product result = productService.getProductById(99);
        assertNull(result);
    }

    @Test
    void getAllProductsTest() {
        when(productDAO.findAll()).thenReturn(List.of(product));
        List<Product> products = productService.getAllProducts();
        assertEquals(products, List.of(product));
    }

    @Test
    void updateProductTest() {
        when(productDAO.findById(product.getId())).thenReturn(Optional.of(product));
        String result = productService.updateProduct(product);
        assertEquals("Product Updated", result);
        verify(productDAO).save(product);
    }

    @Test
    void deleteProductTest() {
        when(productDAO.findById(3)).thenReturn(Optional.of(product));
        String result = productService.deleteProduct(3);
        assertEquals("Product Deleted", result);
        verify(productDAO).deleteById(3);
    }
}