package com.arun.E_Commerce.Project.DAO;

import com.arun.E_Commerce.Project.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDAO extends JpaRepository<Order, Integer> {
}
