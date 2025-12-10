package com.taleju.rms.controller;

import com.taleju.rms.dto.KitchenOrderRequest;
import com.taleju.rms.dto.KitchenOrderResponse;
import com.taleju.rms.service.KitchenOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kitchen-orders")
public class KitchenOrderController {

    @Autowired
    private KitchenOrderService kitchenOrderService;

    // Get all kitchen orders
    @GetMapping
    public ResponseEntity<List<KitchenOrderResponse>> getAllOrders() {
        return ResponseEntity.ok(kitchenOrderService.getAllKitchenOrders());
    }



    // Update kitchen order status
    @PutMapping("/{id}/status")
    public ResponseEntity<KitchenOrderResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody KitchenOrderRequest request) {
        return ResponseEntity.ok(kitchenOrderService.updateStatus(id, request.getStatus()));
    }
}
