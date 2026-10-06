package com.arun.E_Commerce.Project.Service;

import com.arun.E_Commerce.Project.DAO.*;
import com.arun.E_Commerce.Project.Exception.UnauthorizedAccessException;
import com.arun.E_Commerce.Project.Model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private CartDAO cartDAO;
    @Autowired
    private CartItemDAO cartItemDAO;
    @Autowired
    OrderItemDAO orderItemDAO;
    @Autowired
    ProductDAO productDAO;
    @Autowired
    private UserDAO userDAO;

    @Transactional
    public ResponseEntity<String> createOrder(int cartId){
        User user = getAuthUser();
        Cart cart = cartDAO.findById(cartId).orElse(null);
        if(cart == null){
            return new ResponseEntity<>("Cart Is Empty", HttpStatus.NOT_FOUND);
        }
        if(user.getRole().equals("ADMIN")){
            return new ResponseEntity<>(handleCreateOrder(cartId, cart), HttpStatus.OK);
        } else if(user.getRole().equals("USER") && user.getId().equals(cart.getUser().getId())){
            return new ResponseEntity<>(handleCreateOrder(cartId, cart), HttpStatus.OK);
        }
        throw new UnauthorizedAccessException("You are not allowed to create an order");
    }

    public ResponseEntity<Order> getOrderById(int orderId){
        Order order = orderDAO.findById(orderId).orElse(null);
        User user = getAuthUser();
        if(order == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(user.getRole().equals("USER") && user.getId().equals(order.getUser().getId())){
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else if(user.getRole().equals("ADMIN")){
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        throw new UnauthorizedAccessException("You are not allowed to access this order");
    }

    public ResponseEntity<List<Order>> getAllOrders(){
        User user = getAuthUser();
        if(user.getRole().equals("ADMIN")){
            return new ResponseEntity<>(orderDAO.findAll(), HttpStatus.OK);
        }
        throw new UnauthorizedAccessException("You are not allowed to access all the orders");
    }

    public User getAuthUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userDAO.findByEmail(email);
    }

    public String handleCreateOrder(int cartId, Cart cart){
        List<CartItem> cartItem = cartItemDAO.findAllByCartId(cartId);
        if(cart != null && !cartItem.isEmpty()){
            BigDecimal total = BigDecimal.ZERO;
            Order order = new Order();
            for(CartItem c : cartItem){
                Product product = c.getProduct();
                int quantity = c.getQuantity();
                if(quantity <= product.getQuantity()){
                    BigDecimal price = product.getPrice();
                    BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(quantity));
                    total = total.add(itemTotal);
                    order.setAmount(total);
                }
                else{
                    return "Insufficient Stock";
                }
            }
            order.setUser(cart.getUser());
            order.setIssuedDate(LocalDate.now());
            order.setStatus("PLACED");
            orderDAO.save(order);
            for(CartItem c : cartItem){
                Product product = c.getProduct();
                int quantity = c.getQuantity();
                BigDecimal price = product.getPrice();
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setPrice(price);
                orderItem.setProduct(product);
                orderItem.setQuantity(quantity);
                orderItemDAO.save(orderItem);
                product.setQuantity(product.getQuantity() - quantity);
                productDAO.save(product);
                cartItemDAO.deleteById(c.getId());
            }
            return "Order Creation Successful";
        }
        return "Cart Is Empty";
    }
}
