package com.taleju.rms.dto;

import com.taleju.rms.enums.KitchenOrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KitchenOrderResponse {
    private Long id;
    private Long orderId;
    private KitchenOrderStatus status;
    private LocalDateTime timestamp;
}
