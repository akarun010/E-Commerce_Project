package com.arun.E_Commerce.Project.Controller;

import com.arun.E_Commerce.Project.Model.Order;
import com.arun.E_Commerce.Project.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/orders/{cartId}")
    public String createOrder(@PathVariable int cartId){
        return  orderService.createOrder(cartId);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable int orderId){
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders(){
        return orderService.getAllOrders();
    }
}
