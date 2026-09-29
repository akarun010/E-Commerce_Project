package com.arun.E_Commerce.Project.Controller;

import com.arun.E_Commerce.Project.Model.Product;
import com.arun.E_Commerce.Project.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/product")
    public String createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }

    @GetMapping("/product")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/product/{productId}")
    public Product getProductsById(@PathVariable int productId){
        return productService.getProductById(productId);
    }

    @PutMapping("/product")
    public String updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }
}
