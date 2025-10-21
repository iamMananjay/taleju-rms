package com.taleju.rms.dto;

import lombok.Data;

@Data
public class RestaurantRequest {
    private String name;
    private String address;
    private Long subscriptionPlanId; // optional
}
