package com.taleju.rms.service;

import com.taleju.rms.dto.SubscriptionRequest;
import com.taleju.rms.dto.SubscriptionResponse;
import com.taleju.rms.entity.Restaurant;
import com.taleju.rms.entity.Subscription;
import com.taleju.rms.entity.SubscriptionPlan;
import com.taleju.rms.repository.RestaurantRepository;
import com.taleju.rms.repository.SubscriptionPlanRepository;
import com.taleju.rms.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private SubscriptionPlanRepository planRepository;

    public SubscriptionResponse createSubscription(SubscriptionRequest request) {
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        SubscriptionPlan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        Subscription subscription = new Subscription();
        subscription.setRestaurant(restaurant);
        subscription.setPlan(plan);
        subscription.setStartDate(request.getStartDate());
        subscription.setEndDate(request.getEndDate());
        subscription.setStatus(request.getStatus());
        subscriptionRepository.save(subscription);
        restaurant.setSubscriptionPlan(plan);
        restaurantRepository.save(restaurant);

        return mapToResponse(subscription);
    }

    public List<SubscriptionResponse> getAllSubscriptions() {
        return subscriptionRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public SubscriptionResponse updateSubscription(Long id, SubscriptionRequest request) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));


        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        SubscriptionPlan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan not found"));
        subscription.setPlan(plan);

        if(request.getStartDate() != null) subscription.setStartDate(request.getStartDate());
        if(request.getEndDate() != null) subscription.setEndDate(request.getEndDate());
        if(request.getStatus() != null) subscription.setStatus(request.getStatus());

        subscriptionRepository.save(subscription);
        restaurant.setSubscriptionPlan(plan);
        restaurantRepository.save(restaurant);
        return mapToResponse(subscription);
    }

    public void deleteSubscription(Long id) {
        // Fetch subscription
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        // Unlink subscription plan from restaurant
        Restaurant restaurant = subscription.getRestaurant();
        if (restaurant != null) {
            restaurant.setSubscriptionPlan(null);
            restaurantRepository.save(restaurant);
        }
        subscriptionRepository.deleteById(id);
    }

    private SubscriptionResponse mapToResponse(Subscription subscription){
        return new SubscriptionResponse(
                subscription.getId(),
                subscription.getRestaurant() != null ? subscription.getRestaurant().getId() : null,
                subscription.getPlan() != null ? subscription.getPlan().getId() : null,
                subscription.getStartDate(),
                subscription.getEndDate(),
                subscription.getStatus()
        );
    }
}

