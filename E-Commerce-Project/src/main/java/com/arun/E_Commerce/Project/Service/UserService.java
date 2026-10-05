package com.arun.E_Commerce.Project.Service;

import com.arun.E_Commerce.Project.DAO.UserDAO;
import com.arun.E_Commerce.Project.Exception.UnauthorizedAccessException;
import com.arun.E_Commerce.Project.Model.Cart;
import com.arun.E_Commerce.Project.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        return "User Created";
    }

    public ResponseEntity<User> getUserById(int userId){
        User user = getAuthUser();
        if(user.getRole().equals("USER") && userId == user.getId()){
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else if(user.getRole().equals("ADMIN")){
            return new ResponseEntity<>(userDAO.findById(userId).orElse(null), HttpStatus.OK);
        }
        throw new UnauthorizedAccessException("You are not allowed to access this user");
    }

    public List<User> getAllUsers(){
        return userDAO.findAll();
    }

    public ResponseEntity<String> updateUser(User user){
        User existingUser = getAuthUser();
        if(existingUser.getRole().equals("USER") && existingUser.getId() == user.getId()){
            existingUser.setPhone(user.getPhone());
            existingUser.setAddress(user.getAddress());
            existingUser.setName(user.getName());
            userDAO.save(existingUser);
            return new ResponseEntity<>("User Updated", HttpStatus.OK);
        } else if(existingUser.getRole().equals("ADMIN")){
            User targetUser = userDAO.findById(user.getId()).orElse(null);
            if(targetUser != null){
                targetUser.setPhone(user.getPhone());
                targetUser.setAddress(user.getAddress());
                targetUser.setName(user.getName());
                userDAO.save(targetUser);
                return new ResponseEntity<>("User Updated", HttpStatus.OK);
            }
            return new ResponseEntity<>("Couldn't Find User", HttpStatus.NOT_FOUND);
        }
        throw new UnauthorizedAccessException("You are not allowed to access this user");
    }

    public String deleteUser(int id){
        userDAO.deleteById(id);
        return "User Deleted";
    }

    public User getAuthUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userDAO.findByEmail(email);
    }
}
