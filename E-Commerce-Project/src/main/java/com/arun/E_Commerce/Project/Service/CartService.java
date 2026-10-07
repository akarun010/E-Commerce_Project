package com.arun.E_Commerce.Project.Service;

import com.arun.E_Commerce.Project.DAO.CartDAO;
import com.arun.E_Commerce.Project.DAO.CartItemDAO;
import com.arun.E_Commerce.Project.DAO.ProductDAO;
import com.arun.E_Commerce.Project.DAO.UserDAO;
import com.arun.E_Commerce.Project.Exception.UnauthorizedAccessException;
import com.arun.E_Commerce.Project.Model.Cart;
import com.arun.E_Commerce.Project.Model.CartItem;
import com.arun.E_Commerce.Project.Model.Product;
import com.arun.E_Commerce.Project.Model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CartService {
    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private CartItemDAO cartItemDAO;
    @Autowired
    private UserDAO userDAO;

    public ResponseEntity<Cart> viewCart(int id){
        Cart cart = cartDAO.findById(id).orElse(null);
        User user = getAuthUser();
        if(cart == null){
            log.warn("Cart {} Not Found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(user.getRole().equals("ADMIN")){
            log.info("Admin Accessing Cart {}", id);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } else if(user.getRole().equals("USER") && user.getId().equals(cart.getUser().getId())){
            log.info("User Accessing Cart {}", id);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        log.warn("You are not allowed to view this cart");
        throw new UnauthorizedAccessException("You are not allowed to view this cart");
    }

    public ResponseEntity<String> addToCart(int productId, int cartId, int quantity){
        User user = getAuthUser();
        Cart cart = cartDAO.findById(cartId).orElse(null);
        if(cart == null){
            log.warn("Cart {} Not Found", cartId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(user.getRole().equals("ADMIN")) {
            log.info("Admin Adding A Product {} To Cart {}", productId, cartId);
            return new ResponseEntity<>(handleAddCart(cart, productId, quantity), HttpStatus.OK);
        } else if(user.getRole().equals("USER") && user.getId().equals(cart.getUser().getId())) {
            log.info("User Adding A Product {} To Cart {}", productId, cartId);
            return new ResponseEntity<>(handleAddCart(cart, productId, quantity), HttpStatus.OK);
        }
        log.warn("You are not allowed to add to this cart");
        throw new UnauthorizedAccessException("You are not allowed to add to this cart");
    }

    public ResponseEntity<String> updateQuantity(int cartItemId, int quantity){
        User user = getAuthUser();
        CartItem cartItem = cartItemDAO.findById(cartItemId).orElse(null);
        if(cartItem == null){
            log.warn("CartItem {} Not Found", cartItemId);
            return new ResponseEntity<>("Cart Item Doesn't Exist",HttpStatus.NOT_FOUND);
        }
        if(user.getRole().equals("ADMIN")) {
            log.info("Admin Updating A CartItem {}", cartItemId);
            return new ResponseEntity<>(handleUpdateCart(quantity, cartItem), HttpStatus.OK);
        } else if(user.getRole().equals("USER") && user.getId().equals(cartItem.getCart().getUser().getId())){
            log.info("User Updating A CartItem {}", cartItemId);
            return new ResponseEntity<>(handleUpdateCart(quantity, cartItem), HttpStatus.OK);
        }
        log.warn("You are not allowed to update this cart");
        throw new UnauthorizedAccessException("You are not allowed to update this cart");
    }

    public ResponseEntity<String> deleteCartItem(int cartItemId){
        User user = getAuthUser();
        CartItem cartItem = cartItemDAO.findById(cartItemId).orElse(null);
        if(cartItem == null){
            log.warn("CartItem {} Not Found", cartItemId);
            return new ResponseEntity<>("Cart Item Doesn't Exist", HttpStatus.NOT_FOUND);
        } if(user.getRole().equals("ADMIN")){
            cartItemDAO.deleteById(cartItemId);
            log.info("Admin Deleting A CartItem {}", cartItemId);
            return new ResponseEntity<>("Cart Item Deleted", HttpStatus.OK);
        } else if(user.getRole().equals("USER") && user.getId().equals(cartItem.getCart().getUser().getId())){
            cartItemDAO.deleteById(cartItemId);
            log.info("User Deleting A CartItem {}", cartItemId);
            return new ResponseEntity<>("Cart Item Deleted", HttpStatus.OK);
        }
        log.warn("You are not allowed to delete this cart");
        throw new UnauthorizedAccessException("You are not allowed to delete this cart");
    }

    public User getAuthUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userDAO.findByEmail(email);
    }

    public String handleAddCart(Cart cart, int productId, Integer quantity){
        Product product = productDAO.findById(productId).orElse(null);
        if(cart != null && product != null && quantity > 0 && quantity <= product.getQuantity()){
            CartItem existingCartItem = cartItemDAO.findByProductAndCart(product, cart);
            if(existingCartItem == null){
                CartItem cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setCart(cart);
                cartItem.setQuantity(quantity);
                cartItemDAO.save(cartItem);
            }
            else if(existingCartItem.getQuantity() + quantity <= product.getQuantity()){
                existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
                cartItemDAO.save(existingCartItem);
            }
            else{
                log.warn("Insufficient Stock In Product {}", productId);
                return "Insufficient Stock";
            }
            log.info("Cart {} Added", cart.getId());
            return "Cart Added";
        }
        log.warn("Couldn't Add Product {} To Cart", productId);
        return "Couldn't Add To Cart";
    }

    public String handleUpdateCart(int quantity, CartItem cartItem){
        if(quantity > 0 && quantity <= cartItem.getProduct().getQuantity()){
            cartItem.setQuantity(quantity);
            cartItemDAO.save(cartItem);
            log.info("Quantity Updated");
            return "Quantity Updated";
        }
        log.warn("Invalid Quantity {}", quantity);
        return "Invalid Quantity";
    }
}
