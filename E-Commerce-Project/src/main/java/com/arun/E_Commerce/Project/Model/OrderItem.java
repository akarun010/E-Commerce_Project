package com.arun.E_Commerce.Project.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString(exclude = {"order", "product"})
@Table(name = "orderItems")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonBackReference("order-orderItem")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonBackReference("orderItem-products")
    private Product product;

    @NotNull(message = "Quantity Can't Be Empty")
    @Positive(message = "Quantity Must Be Greater Than 0")
    private Integer quantity;

    @Digits(fraction = 2, integer = 6, message = "Invalid Price")
    @NotNull(message = "Price Can't Be Empty")
    @Positive(message = "Price Must Be A Positive Value")
    private BigDecimal price;
}
