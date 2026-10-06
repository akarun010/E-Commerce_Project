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
@Getter @Setter
@ToString(exclude = {"user", "orderItems"})
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-orders")
    private User user;

    @Digits(fraction = 2, integer = 6, message = "Invalid Amount")
    @NotNull(message = "Amount Can't Be Empty")
    @Positive(message = "Amount Must Be A Positive Value")
    private BigDecimal amount;
    private LocalDate issuedDate;
    @NotBlank(message = "Order Status Can't Be Empty")
    @Size(min = 5, max = 30, message = "Order Status Must Between 5 And 30 Characters")
    private String status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("order-orderItem")
    private List<OrderItem> orderItems = new ArrayList<>();
}