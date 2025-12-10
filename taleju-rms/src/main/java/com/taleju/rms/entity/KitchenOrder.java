package com.taleju.rms.entity;

import com.taleju.rms.enums.KitchenOrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "kitchen_orders")
@Data
@NoArgsConstructor
public class KitchenOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // Link to the main order

    @Enumerated(EnumType.STRING)
    private KitchenOrderStatus status = KitchenOrderStatus.NEW;

    private LocalDateTime timestamp = LocalDateTime.now();
}
