package com.taleju.rms.dto;

import com.taleju.rms.enums.OrderStatus;
import com.taleju.rms.enums.OrderType;
import com.taleju.rms.enums.PaymentStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {
    private String orderNumber;
    private Double total;
    private PaymentStatus paymentStatus;
    private OrderStatus orderStatus;
    private OrderType orderType;
    private List<OrderItemResponse> items;
}
