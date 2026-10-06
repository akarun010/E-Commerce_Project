package com.arun.E_Commerce.Project.Controller;

import com.arun.E_Commerce.Project.Model.User;
import com.arun.E_Commerce.Project.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/users")
    public String createUser(@RequestBody @Valid User user){
        return userService.createUser(user);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId){
        return userService.getUserById(userId);
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PutMapping("/users")
    public ResponseEntity<String> updateUser(@RequestBody @Valid User user){
        return userService.updateUser(user);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId){
        return userService.deleteUser(userId);
    }
}
