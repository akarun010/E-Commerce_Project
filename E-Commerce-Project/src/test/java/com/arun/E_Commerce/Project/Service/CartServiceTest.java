package com.arun.E_Commerce.Project.Service;

import com.arun.E_Commerce.Project.DAO.*;
import com.arun.E_Commerce.Project.Model.Cart;
import com.arun.E_Commerce.Project.Model.CartItem;
import com.arun.E_Commerce.Project.Model.Product;
import com.arun.E_Commerce.Project.Model.User;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @InjectMocks CartService cartService;
    @Mock CartDAO cartDAO;
    @Mock CartItemDAO cartItemDAO;
    @Mock UserDAO userDAO;
    @Mock ProductDAO productDAO;
    @Mock Authentication authentication;
    static User user = null;
    static CartItem cartItem = null;
    static Product product = null;
    static Cart cart = null;

    @BeforeAll
    static void demoCart(){
        user = new User();
        user.setName("Arun");
        user.setRole("ADMIN");
        user.setId(2);
        user.setPassword("arun78");
        user.setAddress("Vellakovil");
        user.setPhone("9567890969");

        product = new Product();
        product.setId(2);
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
        cartItem.setQuantity(12);
        cartItem.setProduct(product);
    }

    @Test
    void viewCartTest() {
        when(authentication.getName()).thenReturn("user@gmail.com");
        when(userDAO.findByEmail("user@gmail.com")).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(cartDAO.findById(2)).thenReturn(Optional.of(cart));
        ResponseEntity<Cart> result = cartService.viewCart(2);
        assertEquals(cart, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(cartDAO).findById(2);
    }

    @Test
    void addToCartTest() {
        when(authentication.getName()).thenReturn("user@gmail.com");
        when(userDAO.findByEmail("user@gmail.com")).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(cartDAO.findById(2)).thenReturn(Optional.of(cart));
        when(productDAO.findById(2)).thenReturn(Optional.of(product));
        when(cartItemDAO.findByProductAndCart(product, cart)).thenReturn(cartItem);
        ResponseEntity<String> result = cartService.addToCart(2, 2, 3);
        assertEquals("Cart Added", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(cartItemDAO).save(cartItem);
    }

    @Test
    void updateQuantityTest() {
        when(authentication.getName()).thenReturn("user@gmail.com");
        when(userDAO.findByEmail("user@gmail.com")).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(cartItemDAO.findById(2)).thenReturn(Optional.of(cartItem));
        ResponseEntity<String> result = cartService.updateQuantity(2, 4);
        assertEquals("Quantity Updated", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(cartItemDAO).findById(2);
        verify(cartItemDAO).save(cartItem);
    }

    @Test
    void deleteCartItemTest() {
        when(authentication.getName()).thenReturn("user@gmail.com");
        when(userDAO.findByEmail("user@gmail.com")).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(cartItemDAO.findById(2)).thenReturn(Optional.of(cartItem));
        ResponseEntity<String> result = cartService.deleteCartItem(2);
        assertEquals("Cart Item Deleted", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(cartItemDAO).findById(2);
        verify(cartItemDAO).deleteById(2);
    }

    @Test
    void getAuthUserTest() {
        when(authentication.getName()).thenReturn("user@gmail.com");
        when(userDAO.findByEmail("user@gmail.com")).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User result = cartService.getAuthUser();
        assertEquals(user, result);
        verify(authentication).getName();
        verify(userDAO).findByEmail("user@gmail.com");
    }

    @Test
    void viewCartNotFoundTest(){
        when(authentication.getName()).thenReturn("user@gmail.com");
        when(userDAO.findByEmail("user@gmail.com")).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(cartDAO.findById(99)).thenReturn(Optional.empty());
        ResponseEntity<Cart> result = cartService.viewCart(99);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(cartDAO).findById(99);
    }
}