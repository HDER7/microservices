package com.hder.orders_service.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "Order")
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItems> orderItems;

}
