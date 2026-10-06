package com.arun.E_Commerce.Project.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"cartItems", "orderItems", "category"})
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Product Name Can't Be Empty")
    @Size(min = 2, max = 30, message = "Product Name Must Between 2 And 30 Characters")
    private String name;
    @NotBlank(message = "Product Description Can't Be Empty")
    @Size(min = 10, max = 60, message = "Product Description Must Between 10 And 60 Characters")
    private String description;
    @Digits(fraction = 2, integer = 6, message = "Invalid Price")
    @NotNull(message = "Price Can't Be Empty")
    @Positive(message = "Price Must Be A Positive Value")
    private BigDecimal price;
    @Positive(message = "Quantity Must Be Greater Than 0")
    @NotNull(message = "Quantity Can't Be Empty")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference("category-products")
    private Category category;

    private LocalDate createdAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("orderItem-products")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("cartItem-products")
    private List<CartItem> cartItems = new ArrayList<>();
}
