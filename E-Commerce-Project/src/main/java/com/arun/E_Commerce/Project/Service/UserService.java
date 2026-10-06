package com.arun.E_Commerce.Project.Service;

import com.arun.E_Commerce.Project.DAO.UserDAO;
import com.arun.E_Commerce.Project.Exception.UnauthorizedAccessException;
import com.arun.E_Commerce.Project.Model.Cart;
import com.arun.E_Commerce.Project.Model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String createUser(User user){
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.save(user);
        log.info("User Created {}", user.getId());
        return "User Created";
    }

    public ResponseEntity<User> getUserById(int userId){
        User user = getAuthUser();
        if(user.getRole().equals("USER") && userId == user.getId()){
            log.info("User Accessing Info {}", userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else if(user.getRole().equals("ADMIN")){
            log.info("Admin Accessing Info {}", userId);
            return new ResponseEntity<>(userDAO.findById(userId).orElse(null), HttpStatus.OK);
        }
        log.warn("You are not allowed to access this user");
        throw new UnauthorizedAccessException("You are not allowed to access this user");
    }

    public ResponseEntity<List<User>> getAllUsers(){
        User user = getAuthUser();
        if(user.getRole().equals("ADMIN")){
            log.info("Admin Accessing Users Info");
            return new ResponseEntity<>(userDAO.findAll(), HttpStatus.OK);
        }
        log.warn("You are not allowed to access all the users");
        throw new UnauthorizedAccessException("You are not allowed to access all the users");
    }

    public ResponseEntity<String> updateUser(User user){
        User existingUser = getAuthUser();
        if(existingUser.getRole().equals("USER") && existingUser.getId().equals(user.getId())){
            existingUser.setPhone(user.getPhone());
            existingUser.setAddress(user.getAddress());
            existingUser.setName(user.getName());
            userDAO.save(existingUser);
            log.info("User {} Updated", user.getId());
            return new ResponseEntity<>("User Updated", HttpStatus.OK);
        } else if(existingUser.getRole().equals("ADMIN")){
            User targetUser = userDAO.findById(user.getId()).orElse(null);
            if(targetUser != null){
                targetUser.setPhone(user.getPhone());
                targetUser.setAddress(user.getAddress());
                targetUser.setName(user.getName());
                userDAO.save(targetUser);
                log.info("Admin Updated User {}", user.getId());
                return new ResponseEntity<>("User Updated", HttpStatus.OK);
            }
            log.warn("Couldn't Find User {}", user.getId());
            return new ResponseEntity<>("Couldn't Find User", HttpStatus.NOT_FOUND);
        }
        log.warn("You are not allowed to access this user");
        throw new UnauthorizedAccessException("You are not allowed to access this user");
    }

    public ResponseEntity<String> deleteUser(int userId){
        User user = getAuthUser();
        if(user.getRole().equals("USER") && userId == user.getId()){
            userDAO.deleteById(userId);
            log.info("User {} Deleted", userId);
            return new ResponseEntity<>("User Deleted", HttpStatus.OK);
        } else if(user.getRole().equals("ADMIN")){
            userDAO.deleteById(userId);
            log.info("Admin Deleted User {}", userId);
            return new ResponseEntity<>("User Deleted", HttpStatus.OK);
        }
        log.warn("You are not allowed to access this user");
        throw new UnauthorizedAccessException("You are not allowed to access this user");
    }

    public User getAuthUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userDAO.findByEmail(email);
    }
}
