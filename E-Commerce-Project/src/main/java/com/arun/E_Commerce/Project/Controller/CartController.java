package com.arun.E_Commerce.Project.Controller;

import com.arun.E_Commerce.Project.Model.Cart;
import com.arun.E_Commerce.Project.Service.CartService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<Cart> viewCart(@PathVariable int cartId){
        return cartService.viewCart(cartId);
    }

    @PostMapping("/cart/{cartId}/product/{productId}/quantity/{quantity}")
    public ResponseEntity<String> addToCart(
            @PathVariable("cartId") int cartId,
            @PathVariable("quantity")
            @Positive(message = "Quantity Must Be Greater Than 0") Integer quantity,
            @PathVariable("productId") int productId
    ){
        return cartService.addToCart(productId,cartId,quantity);
    }

    @PutMapping("/cart/{cartItemId}/quantity/{quantity}")
    public ResponseEntity<String> updateQuantity(@PathVariable("cartItemId") int cartItemId,
                                                 @PathVariable("quantity")
                                                 @Positive(message = "Quantity Must Be Greater Than 0") Integer quantity){
        return cartService.updateQuantity(cartItemId, quantity);
    }

    @DeleteMapping("/cart/{cartItemId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable int cartItemId){
        return cartService.deleteCartItem(cartItemId);
    }
}
