package com.taleju.rms.controller;

import com.taleju.rms.dto.OrderRequest;
import com.taleju.rms.dto.OrderResponse;
import com.taleju.rms.enums.OrderStatus;
import com.taleju.rms.enums.PaymentStatus;
import com.taleju.rms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Place new order
    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request) {
        // Get logged-in email from JWT token
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        // If not authenticated, it will be "anonymousUser"
        if ("anonymousUser".equals(loggedInEmail)) {
            loggedInEmail = null; // treat as guest order
        }

        return ResponseEntity.ok(orderService.placeOrder(request,loggedInEmail));
    }

    // Get all orders by restaurant
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(orderService.getOrdersByRestaurant(restaurantId));
    }

    // Get order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    // Update order status
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long orderId,
                                                           @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }

    // Update payment status
    @PutMapping("/{orderId}/payment")
    public ResponseEntity<OrderResponse> updatePaymentStatus(@PathVariable Long orderId,
                                                             @RequestParam PaymentStatus status) {
        return ResponseEntity.ok(orderService.updatePaymentStatus(orderId, status));
    }

    // Update order items before it is processed
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long orderId,
                                                     @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.updateOrder(orderId, request));
    }


    // Cancel order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order cancelled successfully");
    }
}
