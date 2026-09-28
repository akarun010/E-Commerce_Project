package com.arun.E_Commerce.Project.Controller;

import com.arun.E_Commerce.Project.Model.Cart;
import com.arun.E_Commerce.Project.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("/cart/{cartId}")
    public Cart viewCart(@PathVariable int cartId){
        return cartService.viewCart(cartId);
    }

    @PostMapping("/cart/{cartId}/product/{productId}/quantity/{quantity}")
    public String addToCart(
            @PathVariable("cartId") int cartId,
            @PathVariable("quantity") int quantity,
            @PathVariable("productId") int productId
    ){
        return cartService.addToCart(productId,cartId,quantity);
    }

    @PutMapping("/cart/{cartItemId}/quantity/{quantity}")
    public String updateQuantity(@PathVariable("cartItemId") int cartItemId, @PathVariable("quantity") int quantity){
        return cartService.updateQuantity(cartItemId, quantity);
    }

    @DeleteMapping("/cart/{cartItemId}")
    public String deleteCartItem(@PathVariable int cartItemId){
        return cartService.deleteCartItem(cartItemId);
    }
}
