package com.taleju.rms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StaffResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
    private Long restaurantId;    // optional — only if multi-branch
}
