package com.arun.E_Commerce.Project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Order {
    @Id
    private int id;
    private int userId;
    private double amount;
    private Date issuedAt;
    private String status;
}
