package com.hder.orders_service.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sku;
    private Double price;
    private Long quantity;
    @ManyToOne
    @JoinColumn(name = "oder_id")
    private Order order;
}
