package com.arun.E_Commerce.Project.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@ToString(exclude = {"orders", "cart"})
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 30, min = 2, message = "Name Must Be Between 2 And 30 Characters")
    @NotBlank(message = "Name Can't Be Empty")
    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Email Must Be A Proper Gmail Account")
    @NotBlank(message = "Email Can't Be Empty")
    private String email;
    @NotBlank(message = "Password Can't Be Empty")
    @Size(min = 8, message = "Password Must Be Greater Than 7 Character")
    private String password;
    @NotBlank(message = "Phone Number Can't Be Empty")
    @Pattern(regexp = "^\\d{10}$", message = "Phone Number Must Be 10 Digits")
    private String phone;
    @NotBlank(message = "Address Can't Be Empty")
    @Size(min = 5, max = 60, message = "Address Must Be Between 5 And 60 Characters")
    private String address;
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("user-orders")
    private List<Order> orders = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("user-cart")
    private Cart cart;
}