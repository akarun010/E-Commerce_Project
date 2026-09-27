package com.arun.E_Commerce.Project.DAO;

import com.arun.E_Commerce.Project.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDAO extends JpaRepository<Order, Integer> {
}
