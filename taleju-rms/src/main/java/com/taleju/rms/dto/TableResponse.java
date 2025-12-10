package com.taleju.rms.dto;

import lombok.Data;

@Data
public class TableResponse {
    private Long id;
    private String tableNumber;
    private Long restaurantID;
}
