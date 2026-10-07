package com.arun.E_Commerce.Project.Service;

import com.arun.E_Commerce.Project.DAO.UserDAO;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserDAO userDAO;
    @InjectMocks
    UserService userService;
    static User user = null;
    @Mock
    BCryptPasswordEncoder passwordEncoder;
    @Mock
    Authentication authentication;

    @BeforeAll
    static void demoUser(){
        user = new User();
        user.setName("Arun");
        user.setRole("ADMIN");
        user.setId(1);
        user.setPassword("arun78");
        user.setAddress("Vellakovil");
        user.setPhone("9567890969");
    }

    @Test
    void createUserTest() {
        String result = userService.createUser(user);
        verify(userDAO).save(user);
        assertEquals("User Created", result);
    }

    @Test
    void getUserByIdTest() {
        when(userDAO.findById(1)).thenReturn(Optional.of(user));
        when(authentication.getName()).thenReturn("user@gmail.com");
        when(userDAO.findByEmail("user@gmail.com")).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ResponseEntity<User> result = userService.getUserById(1);
        verify(userDAO).findById(1);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(user, result.getBody());
    }

    @Test
    void getAllUsersTest() {
        when(userDAO.findAll()).thenReturn(List.of(user));
        when(authentication.getName()).thenReturn("user@gmail.com");
        when(userDAO.findByEmail("user@gmail.com")).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ResponseEntity<List<User>> result = userService.getAllUsers();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(user), result.getBody());
        verify(userDAO).findAll();
    }

    @Test
    void updateUserTest() {
        when(authentication.getName()).thenReturn("user@gmail.com");
        when(userDAO.findByEmail("user@gmail.com")).thenReturn(user);
        when(userDAO.findById(1)).thenReturn(Optional.of(user));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ResponseEntity<String> result = userService.updateUser(user);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("User Updated", result.getBody());
        verify(userDAO).save(user);
    }

    @Test
    void deleteUserTest() {
        when(authentication.getName()).thenReturn("user@gmail.com");
        when(userDAO.findByEmail("user@gmail.com")).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ResponseEntity<String> result = userService.deleteUser(1);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("User Deleted", result.getBody());
        verify(userDAO).deleteById(1);
    }

    @Test
    void getAuthUserTest() {
        when(authentication.getName()).thenReturn("user@gmail.com");
        when(userDAO.findByEmail("user@gmail.com")).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User result = userService.getAuthUser();
        assertEquals(user, result);
        verify(authentication).getName();
        verify(userDAO).findByEmail("user@gmail.com");
    }
}