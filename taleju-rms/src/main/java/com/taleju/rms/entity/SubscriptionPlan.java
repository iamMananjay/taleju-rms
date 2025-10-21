package com.taleju.rms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "subscription_plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Starter, Standard, Premium

    @Column(columnDefinition = "TEXT")
    private String features; // e.g., "Till + Kitchen Display + QR Ordering"

    private BigDecimal price; // e.g., 20.00, 50.00, 100.00

    @OneToMany(mappedBy = "subscriptionPlan")
    private Set<Restaurant> restaurants; // Restaurants subscribed to this plan
}
