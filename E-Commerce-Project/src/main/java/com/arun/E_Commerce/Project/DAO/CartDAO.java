package com.arun.E_Commerce.Project.DAO;

import com.arun.E_Commerce.Project.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDAO extends JpaRepository<Cart, Integer> {
}
