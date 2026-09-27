package com.arun.E_Commerce.Project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class OrderItem {
    @Id
    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private double price;
}
