package com.taleju.rms.dto;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Long menuItemId;
    private Integer quantity;
    private String specialRequest;
}
