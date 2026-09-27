package com.arun.E_Commerce.Project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Product {
    @Id
    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private int categoryId;
    private Date createdAt;
}
