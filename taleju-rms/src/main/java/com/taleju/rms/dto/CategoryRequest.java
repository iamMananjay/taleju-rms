package com.taleju.rms.dto;

import lombok.Data;

@Data
public class CategoryRequest {
    private String name;
    private Long restaurantId;
}
