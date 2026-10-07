package com.arun.E_Commerce.Project.Service;

import com.arun.E_Commerce.Project.DAO.*;
import com.arun.E_Commerce.Project.Model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock OrderDAO orderDAO;
    @InjectMocks OrderService orderService;
    @Mock CartDAO cartDAO;
    @Mock CartItemDAO cartItemDAO;
    @Mock OrderItemDAO orderItemDAO;
    @Mock UserDAO userDAO;
    @Mock ProductDAO productDAO;
    @Mock Authentication authentication;

    static Order order = null;
    static User user = null;
    static Cart cart = null;
    static CartItem cartItem = null;
    static Product product = null;

    @BeforeAll
    static void demoOrder(){
        order = new Order();
        order.setId(4);
        order.setStatus("SHIPPED");
        order.setIssuedDate(LocalDate.now());
        order.setAmount(new BigDecimal("400.00"));

        user = new User();
        user.setName("Arun");
        user.setRole("ADMIN");
        user.setId(1);
        user.setPassword("arun78");
        user.setAddress("Vellakovil");
        user.setPhone("9567890969");

        product = new Product();
        product.setId(3);
        product.setCreatedAt(LocalDate.now());
        product.setPrice(new BigDecimal("5000.00"));
        product.setQuantity(20);
        product.setDescription("A Beautiful Toy");
        product.setName("Legos");

        cart = new Cart();
        cart.setId(2);
        cart.setUser(user);

        cartItem = new CartItem();
        cartItem.setId(2);
        cartItem.setQuantity(13);
        cartItem.setCart(cart);
        cartItem.setProduct(product);
    }

    @Test
    void createOrderTest() {
        when(authentication.getName()).thenReturn("user@gmal.com");
        when(userDAO.findByEmail("user@gmal.com")).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(cartDAO.findById(2)).thenReturn(Optional.of(cart));
        when(cartItemDAO.findAllByCartId(2)).thenReturn(List.of(cartItem));
        ResponseEntity<String> result = orderService.createOrder(2);
        assertEquals("Order Creation Successful", result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(productDAO).save(product);
        verify(cartItemDAO).deleteById(any());
        verify(orderItemDAO).save(any());
    }

    @Test
    void getOrderByIdTest() {
        when(authentication.getName()).thenReturn("user@gmal.com");
        when(userDAO.findByEmail("user@gmal.com")).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(orderDAO.findById(4)).thenReturn(Optional.of(order));
        ResponseEntity<Order> result = orderService.getOrderById(4);
        assertEquals(order, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(orderDAO).findById(4);
    }

    @Test
    void getAllOrdersTest() {
        when(authentication.getName()).thenReturn("user@gmal.com");
        when(userDAO.findByEmail("user@gmal.com")).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(orderDAO.findAll()).thenReturn(List.of(order));
        ResponseEntity<List<Order>> result = orderService.getAllOrders();
        assertEquals(List.of(order), result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(orderDAO).findAll();
    }

    @Test
    void getAuthUserTest() {
        when(authentication.getName()).thenReturn("user@gmail.com");
        when(userDAO.findByEmail("user@gmail.com")).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User result = orderService.getAuthUser();
        assertEquals(user, result);
        verify(authentication).getName();
        verify(userDAO).findByEmail("user@gmail.com");
    }

    @Test
    void createOrderCartNotFoundTest(){
        when(authentication.getName()).thenReturn("user@gmal.com");
        when(userDAO.findByEmail("user@gmal.com")).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(cartDAO.findById(99)).thenReturn(Optional.empty());
        ResponseEntity<String> result = orderService.createOrder(99);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(cartDAO).findById(99);
        assertEquals("Cart Is Empty", result.getBody());
    }
}