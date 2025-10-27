package com.taleju.rms.dto;

import lombok.Data;

@Data
public class MenuItemRequest {
    private String name;
    private String description;
    private String image;
    private boolean isAvailable;
    private double price;
    private Long categoryId;
    private Long restaurantId;
}
