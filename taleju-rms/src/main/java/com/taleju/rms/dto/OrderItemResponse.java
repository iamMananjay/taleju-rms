package com.taleju.rms.dto;

import lombok.Data;

@Data
public class OrderItemResponse {
    private String menuItemName;
    private Integer quantity;
    private Double price;
    private String specialRequest;
}
