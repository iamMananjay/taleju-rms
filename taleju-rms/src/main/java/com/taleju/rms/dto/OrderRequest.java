package com.taleju.rms.dto;

import com.taleju.rms.enums.OrderStatus;
import com.taleju.rms.enums.OrderType;
import com.taleju.rms.enums.PaymentStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private OrderType orderType;
    private List<OrderItemRequest> items;
    private Long restaurant;
}
