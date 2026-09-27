package com.arun.E_Commerce.Project.DAO;

import com.arun.E_Commerce.Project.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemDAO extends JpaRepository<CartItem, Integer> {
}
