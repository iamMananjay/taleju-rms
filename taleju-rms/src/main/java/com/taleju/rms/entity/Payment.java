package com.taleju.rms.entity;

import com.taleju.rms.enums.PaymentType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private  Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
}
