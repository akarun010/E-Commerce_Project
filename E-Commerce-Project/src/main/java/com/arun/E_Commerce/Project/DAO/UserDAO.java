package com.arun.E_Commerce.Project.DAO;

import com.arun.E_Commerce.Project.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User,Integer> {
}
