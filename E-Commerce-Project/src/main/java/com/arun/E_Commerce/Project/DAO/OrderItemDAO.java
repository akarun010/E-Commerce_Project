package com.arun.E_Commerce.Project.DAO;

import com.arun.E_Commerce.Project.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemDAO extends JpaRepository<OrderItem, Integer> {
}
