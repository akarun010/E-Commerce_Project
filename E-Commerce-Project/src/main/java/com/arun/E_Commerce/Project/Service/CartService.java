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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(user.getRole().equals("ADMIN")){
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } else if(user.getRole().equals("USER") && user.getId().equals(cart.getUser().getId())){
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        throw new UnauthorizedAccessException("You are not allowed to view this cart");
    }

    public ResponseEntity<String> addToCart(int productId, int cartId, int quantity){
        User user = getAuthUser();
        Cart cart = cartDAO.findById(cartId).orElse(null);
        if(cart == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(user.getRole().equals("ADMIN")) {
            return new ResponseEntity<>(handleAddCart(cart, productId, quantity), HttpStatus.OK);
        } else {
            if(user.getRole().equals("USER")) {
                if (user.getId().equals(cart.getUser().getId())) {
                    return new ResponseEntity<>(handleAddCart(cart, productId, quantity), HttpStatus.OK);
                }
            }
        }
        throw new UnauthorizedAccessException("You are not allowed to add to this cart");
    }

    public ResponseEntity<String> updateQuantity(int cartItemId, int quantity){
        User user = getAuthUser();
        CartItem cartItem = cartItemDAO.findById(cartItemId).orElse(null);
        if(cartItem == null){
            return new ResponseEntity<>("Cart Item Doesn't Exist",HttpStatus.NOT_FOUND);
        }
        if(user.getRole().equals("ADMIN")) {
            return new ResponseEntity<>(handleUpdateCart(quantity, cartItem), HttpStatus.OK);
        } else if(user.getRole().equals("USER") && user.getId().equals(cartItem.getCart().getUser().getId())){
            return new ResponseEntity<>(handleUpdateCart(quantity, cartItem), HttpStatus.OK);
        }
        throw new UnauthorizedAccessException("You are not allowed to update this cart");
    }

    public ResponseEntity<String> deleteCartItem(int cartItemId){
        User user = getAuthUser();
        CartItem cartItem = cartItemDAO.findById(cartItemId).orElse(null);
        if(cartItem == null){
            return new ResponseEntity<>("Cart Item Doesn't Exist", HttpStatus.NOT_FOUND);
        } if(user.getRole().equals("ADMIN")){
            cartItemDAO.deleteById(cartItemId);
            return new ResponseEntity<>("Cart Item Deleted", HttpStatus.OK);
        } else if(user.getRole().equals("USER") && user.getId().equals(cartItem.getCart().getUser().getId())){
            cartItemDAO.deleteById(cartItemId);
            return new ResponseEntity<>("Cart Item Deleted", HttpStatus.OK);
        }
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
                return "Insufficient Stock";
            }

            return "Cart Added";
        }
        return "Couldn't Add To Cart";
    }

    public String handleUpdateCart(int quantity, CartItem cartItem){
        if(quantity > 0 && quantity <= cartItem.getProduct().getQuantity()){
            cartItem.setQuantity(quantity);
            cartItemDAO.save(cartItem);
            return "Quantity Updated";
        }
        return "Invalid Quantity";
    }
}
