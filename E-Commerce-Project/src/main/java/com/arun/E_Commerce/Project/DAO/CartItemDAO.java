package com.arun.E_Commerce.Project.DAO;

import com.arun.E_Commerce.Project.Model.Cart;
import com.arun.E_Commerce.Project.Model.CartItem;
import com.arun.E_Commerce.Project.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartItemDAO extends JpaRepository<CartItem, Integer> {
    CartItem findByProductAndCart(Product product, Cart cart);

    List<CartItem> findAllByCartId(int cartId);
}
