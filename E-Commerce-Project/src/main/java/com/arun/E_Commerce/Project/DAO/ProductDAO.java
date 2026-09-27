package com.arun.E_Commerce.Project.DAO;

import com.arun.E_Commerce.Project.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDAO extends JpaRepository<Product, Integer> {
}
