package com.taleju.rms.dto;

import com.taleju.rms.enums.OrderType;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private OrderType orderType;
    private List<OrderItemRequest> items;
    private Long restaurant;
}
