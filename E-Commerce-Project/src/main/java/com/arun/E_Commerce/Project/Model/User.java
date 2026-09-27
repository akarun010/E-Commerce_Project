package com.arun.E_Commerce.Project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String role;
}
