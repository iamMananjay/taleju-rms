package com.taleju.rms.dto;

import com.taleju.rms.enums.KitchenOrderStatus;
import lombok.Data;

@Data
public class KitchenOrderRequest {
    private KitchenOrderStatus status;
}
