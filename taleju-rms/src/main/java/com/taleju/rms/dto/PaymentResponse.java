package com.taleju.rms.dto;

import com.taleju.rms.enums.PaymentStatus;
import lombok.Data;

@Data
public class PaymentResponse {
    private Long id;
    private Long orderId;



    private Double amount;
    private PaymentStatus paymentStatus;

    public PaymentResponse(Long id, Long orderId, Double amount, PaymentStatus paymentStatus) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }
}

