package com.taleju.rms.dto;

import lombok.Data;

@Data
public class StaffRequest {
    private String name;
    private String email;
    private String password;
    private String role;          // e.g., "STAFF", "CHEF", etc.
    private Long restaurantId;    // optional — only if multi-branch
}
