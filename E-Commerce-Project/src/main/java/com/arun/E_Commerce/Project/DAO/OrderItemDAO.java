package com.arun.E_Commerce.Project.DAO;

import com.arun.E_Commerce.Project.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemDAO extends JpaRepository<OrderItem, Integer> {
}
