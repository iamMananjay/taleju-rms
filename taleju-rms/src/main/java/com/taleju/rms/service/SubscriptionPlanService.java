package com.taleju.rms.service;

import com.taleju.rms.dto.SubscriptionPlanRequest;
import com.taleju.rms.dto.SubscriptionPlanResponse;
import com.taleju.rms.entity.SubscriptionPlan;
import com.taleju.rms.repository.SubscriptionPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionPlanService {

    @Autowired
    private SubscriptionPlanRepository planRepository;

    public SubscriptionPlanResponse createPlan(SubscriptionPlanRequest request) {
        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setName(request.getName());
        plan.setFeatures(request.getFeatures());
        plan.setPrice(request.getPrice());
        planRepository.save(plan);

        return mapToResponse(plan);
    }

    public List<SubscriptionPlanResponse> getAllPlans() {
        return planRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public SubscriptionPlanResponse updatePlan(Long id, SubscriptionPlanRequest request) {
        SubscriptionPlan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription Plan not found"));

        if(request.getName() != null) plan.setName(request.getName());
        if(request.getFeatures() != null) plan.setFeatures(request.getFeatures());
        if(request.getPrice() != null) plan.setPrice(request.getPrice());

        planRepository.save(plan);
        return mapToResponse(plan);
    }

    public void deletePlan(Long id) {
        planRepository.deleteById(id);
    }

    private SubscriptionPlanResponse mapToResponse(SubscriptionPlan plan) {
        List<Long> restaurantIds = plan.getRestaurants() != null ?
                plan.getRestaurants().stream().map(r -> r.getId()).collect(Collectors.toList()) : new ArrayList<>();

        return new SubscriptionPlanResponse(plan.getId(), plan.getName(), plan.getFeatures(), plan.getPrice(), restaurantIds);
    }
}

