package com.arun.E_Commerce.Project.Service;

import com.arun.E_Commerce.Project.DAO.ProductDAO;
import com.arun.E_Commerce.Project.Model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDAO productDAO;

    public String createProduct(Product product) {
        productDAO.save(product);
        return "Product Created";
    }

    public Product getProductById(int id) {
        return productDAO.findById(id).orElse(null);
    }

    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    public String updateProduct(Product product) {
        Product exsistingProduct = productDAO.findById(product.getId()).orElse(null);
        if (exsistingProduct == null) {
            return "Product Not Found";
        }
        exsistingProduct.setName(product.getName());
        exsistingProduct.setDescription(product.getDescription());
        exsistingProduct.setQuantity(product.getQuantity());
        exsistingProduct.setPrice(product.getPrice());
        exsistingProduct.setCreatedAt(product.getCreatedAt());
        exsistingProduct.setCategory(product.getCategory());
        productDAO.save(exsistingProduct);
        return "Product Updated";
    }

    public String deleteProduct(int id) {
        productDAO.deleteById(id);
        return "Product Deleted";
    }
}
