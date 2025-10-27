package com.taleju.rms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemResponse {
    private Long id;
    private String name;
    private String description;
    private String image;
    private boolean isAvailable;
    private double price;
    private Long categoryId;
    private Long restaurantId;
}
