package com.taleju.rms.dto;

import com.taleju.rms.enums.PaymentType;
import lombok.Data;

@Data
public class PaymentRequest {
    private Long orderId;
    private PaymentType PaymentType;
}
