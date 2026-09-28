package com.arun.E_Commerce.Project.Service;

import com.arun.E_Commerce.Project.DAO.CartDAO;
import com.arun.E_Commerce.Project.DAO.CartItemDAO;
import com.arun.E_Commerce.Project.DAO.ProductDAO;
import com.arun.E_Commerce.Project.Model.Cart;
import com.arun.E_Commerce.Project.Model.CartItem;
import com.arun.E_Commerce.Project.Model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartService {
    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private CartItemDAO cartItemDAO;

    public Cart viewCart(int id){
        return cartDAO.findById(id).orElse(null);
    }

    public String addToCart(int productId, int cartId, int quantity){
        Cart cart = cartDAO.findById(cartId).orElse(null);
        Product product = productDAO.findById(productId).orElse(null);
        if(cart != null && product != null && quantity > 0 && quantity <= product.getQuantity()){
            CartItem existingCartItem = cartItemDAO.findByProductExistsAndCart(product, cart);
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

    public String updateQuantity(int cartItemId, int quantity){
        CartItem cartItem = cartItemDAO.findById(cartItemId).orElse(null);
        if(cartItem != null && quantity > 0 && quantity <= cartItem.getProduct().getQuantity()){
            cartItem.setQuantity(quantity);
            cartItemDAO.save(cartItem);
            return "Quantity Updated";
        }
        return "Cart Item Not Found";
    }

    public String deleteCartItem(int cartItemId){
        CartItem cartItem = cartItemDAO.findById(cartItemId).orElse(null);
        if(cartItem == null){
            return "Cart Item Doesn't Exist";
        }
        cartItemDAO.deleteById(cartItemId);
        return "Cart Item Deleted";
    }
}
