package com.taleju.rms.repository;

import com.taleju.rms.entity.KitchenOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  KitchenOrderRepository extends JpaRepository<KitchenOrder,Long> {
    List<KitchenOrder> findByStatus(String status);
    List<KitchenOrder> findByOrderId(Long orderId);
}
