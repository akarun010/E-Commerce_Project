package com.arun.E_Commerce.Project.Service;

import com.arun.E_Commerce.Project.DAO.UserDAO;
import com.arun.E_Commerce.Project.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public String createUser(User user){
        userDAO.save(user);
        return "User Created";
    }

    public User getUserById(int id){
        return userDAO.findById(id).orElse(null);
    }

    public List<User> getAllUsers(){
        return userDAO.findAll();
    }

    public String updateUser(User user){
        User exsistingUser = userDAO.findByEmail(user.getEmail());
        if(exsistingUser == null){
            return "User Not Found";
        }
        exsistingUser.setPhone(user.getPhone());
        exsistingUser.setAddress(user.getAddress());
        exsistingUser.setName(user.getName());
        userDAO.save(exsistingUser);
        return "User Updated";
    }

    public String deleteUser(int id){
        userDAO.deleteById(id);
        return "User Deleted";
    }
}
