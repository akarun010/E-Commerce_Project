package com.arun.E_Commerce.Project.Service;

import com.arun.E_Commerce.Project.DAO.*;
import com.arun.E_Commerce.Project.Model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional
    public String createOrder(int cartId){
        Cart cart = cartDAO.findById(cartId).orElse(null);
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

    public Order getOrderById(int orderId){
        return orderDAO.findById(orderId).orElse(null);
    }

    public List<Order> getAllOrders(){
        return orderDAO.findAll();
    }
}
