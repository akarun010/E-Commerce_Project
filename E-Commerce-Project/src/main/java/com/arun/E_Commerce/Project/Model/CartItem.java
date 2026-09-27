package com.arun.E_Commerce.Project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CartItem {
    @Id
    private int id;
    private int cartId;
    private int productId;
    private int quantity;
}
