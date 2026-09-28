package com.arun.E_Commerce.Project.DAO;

import com.arun.E_Commerce.Project.Model.Cart;
import com.arun.E_Commerce.Project.Model.CartItem;
import com.arun.E_Commerce.Project.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {
}
