package com.arun.E_Commerce.Project.Service;

import com.arun.E_Commerce.Project.DAO.ProductDAO;
import com.arun.E_Commerce.Project.Model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductDAO productDAO;

    public String createProduct(Product product) {
        productDAO.save(product);
        log.info("Product {} Created", product.getId());
        return "Product Created";
    }

    public Product getProductById(int id) {
        Product product = productDAO.findById(id).orElse(null);
        if(product == null){
            log.warn("Product {} Not Found", id);
            return null;
        }
        log.info("Product {} Is Accessed", id);
        return product;
    }

    public List<Product> getAllProducts() {
        log.info("All Products Are Accessed");
        return productDAO.findAll();
    }

    public String updateProduct(Product product) {
        Product exsistingProduct = productDAO.findById(product.getId()).orElse(null);
        if (exsistingProduct == null) {
            log.warn("Product {} Not Found", product.getId());
            return "Product Not Found";
        }
        exsistingProduct.setName(product.getName());
        exsistingProduct.setDescription(product.getDescription());
        exsistingProduct.setQuantity(product.getQuantity());
        exsistingProduct.setPrice(product.getPrice());
        exsistingProduct.setCreatedAt(product.getCreatedAt());
        exsistingProduct.setCategory(product.getCategory());
        productDAO.save(exsistingProduct);
        log.info("Product {} Updated", product.getId());
        return "Product Updated";
    }

    public String deleteProduct(int id) {
        Product exsistingProduct = productDAO.findById(id).orElse(null);
        if(exsistingProduct == null){
            log.warn("Product {} Not Found", id);
            return "Product Not Found";
        }
        productDAO.deleteById(id);
        log.info("Product {} Deleted", id);
        return "Product Deleted";
    }
}
