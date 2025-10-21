package com.taleju.rms.service;

import com.taleju.rms.dto.RestaurantRequest;
import com.taleju.rms.dto.RestaurantResponse;
import com.taleju.rms.entity.Restaurant;
import com.taleju.rms.entity.SubscriptionPlan;
import com.taleju.rms.repository.RestaurantRepository;
import com.taleju.rms.repository.SubscriptionPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private SubscriptionPlanRepository planRepository;

    // CREATE
    public RestaurantResponse createRestaurant(RestaurantRequest request) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.getName());
        restaurant.setAddress(request.getAddress());

        if (request.getSubscriptionPlanId() != null) {
            SubscriptionPlan plan = planRepository.findById(request.getSubscriptionPlanId())
                    .orElseThrow(() -> new RuntimeException("Subscription Plan not found"));
            restaurant.setSubscriptionPlan(plan);
        }

        restaurantRepository.save(restaurant);
        return mapToResponse(restaurant);
    }

    // READ all
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // READ one
    public RestaurantResponse getRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        return mapToResponse(restaurant);
    }

    // UPDATE
    public RestaurantResponse updateRestaurant(Long id, RestaurantRequest request) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        if (request.getName() != null) restaurant.setName(request.getName());
        if (request.getAddress() != null) restaurant.setAddress(request.getAddress());

        if (request.getSubscriptionPlanId() != null) {
            SubscriptionPlan plan = planRepository.findById(request.getSubscriptionPlanId())
                    .orElseThrow(() -> new RuntimeException("Subscription Plan not found"));
            restaurant.setSubscriptionPlan(plan);
        } else {
            restaurant.setSubscriptionPlan(null);
        }

        restaurantRepository.save(restaurant);
        return mapToResponse(restaurant);
    }

    // DELETE
    public void deleteRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        // Remove link to subscription plan
        if (restaurant.getSubscriptionPlan() != null) {
            SubscriptionPlan plan = restaurant.getSubscriptionPlan();
            plan.getRestaurants().remove(restaurant);
            planRepository.save(plan);
        }

        restaurantRepository.deleteById(id);
    }

    private RestaurantResponse mapToResponse(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getSubscriptionPlan() != null ? restaurant.getSubscriptionPlan().getId() : null
        );
    }
}
