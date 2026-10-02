package com.arun.E_Commerce.Project.DAO;

import com.arun.E_Commerce.Project.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User,Integer> {
    User findByEmail(String email);

    User findByUserName(String username);
}
