package com.taleju.rms.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String role; // e.g., MANAGER, STAFF, etc.
    private Long restaurantId;
}
