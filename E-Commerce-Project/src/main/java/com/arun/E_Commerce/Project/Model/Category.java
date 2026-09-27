package com.arun.E_Commerce.Project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Category {
    @Id
    private int id;
    private String name;
    private String description;
}
