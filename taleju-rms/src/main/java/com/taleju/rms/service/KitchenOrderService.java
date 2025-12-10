package com.taleju.rms.service;

import com.taleju.rms.dto.KitchenOrderResponse;
import com.taleju.rms.entity.KitchenOrder;
import com.taleju.rms.entity.Order;
import com.taleju.rms.enums.KitchenOrderStatus;
import com.taleju.rms.repository.KitchenOrderRepository;
import com.taleju.rms.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KitchenOrderService {
    @Autowired
    private KitchenOrderRepository kitchenOrderRepo;

    @Autowired
    private OrderRepository orderRepo;

    // Place a kitchen order (called automatically when customer places order)
    public void createKitchenOrder(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        KitchenOrder kitchenOrder = new KitchenOrder();
        kitchenOrder.setOrder(order);
        kitchenOrder.setStatus(KitchenOrderStatus.NEW);

        KitchenOrder saved = kitchenOrderRepo.save(kitchenOrder);
        mapToResponse(saved);
    }

    // Get all kitchen orders
    public List<KitchenOrderResponse> getAllKitchenOrders() {
        return kitchenOrderRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Update kitchen order status
    public KitchenOrderResponse updateStatus(Long kitchenOrderId, KitchenOrderStatus status) {
        KitchenOrder kitchenOrder = kitchenOrderRepo.findById(kitchenOrderId)
                .orElseThrow(() -> new RuntimeException("Kitchen order not found"));

        kitchenOrder.setStatus(status);
        return mapToResponse(kitchenOrderRepo.save(kitchenOrder));
    }

    private KitchenOrderResponse mapToResponse(KitchenOrder ko) {
        KitchenOrderResponse resp = new KitchenOrderResponse();
        resp.setId(ko.getId());
        resp.setOrderId(ko.getOrder().getId());
        resp.setStatus(ko.getStatus());
        resp.setTimestamp(ko.getTimestamp());
        return resp;
    }
}
