package com.arun.E_Commerce.Project.DAO;

import com.arun.E_Commerce.Project.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Category, Integer> {
}
