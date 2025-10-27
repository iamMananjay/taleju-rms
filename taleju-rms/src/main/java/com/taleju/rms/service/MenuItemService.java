package com.taleju.rms.service;

import com.taleju.rms.dto.MenuItemRequest;
import com.taleju.rms.dto.MenuItemResponse;
import com.taleju.rms.entity.Category;
import com.taleju.rms.entity.MenuItem;
import com.taleju.rms.entity.Restaurant;
import com.taleju.rms.repository.CategoryRepository;
import com.taleju.rms.repository.MenuItemRepository;
import com.taleju.rms.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public MenuItemResponse createMenuItem(MenuItemRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        MenuItem item = new MenuItem();
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setImage(request.getImage());
        item.setAvailable(request.isAvailable());
        item.setPrice(request.getPrice());
        item.setCategory(category);
        item.setRestaurant(restaurant);

        menuItemRepository.save(item);
        return mapToResponse(item);
    }

    public List<MenuItemResponse> getAllMenuItems() {
        return menuItemRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public MenuItemResponse updateMenuItem(Long id, MenuItemRequest request) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        if (request.getName() != null) item.setName(request.getName());
        if (request.getDescription() != null) item.setDescription(request.getDescription());
        if (request.getImage() != null) item.setImage(request.getImage());
        item.setAvailable(request.isAvailable());
        if (request.getPrice() != 0) item.setPrice(request.getPrice());

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            item.setCategory(category);
        }

        if (request.getRestaurantId() != null) {
            Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                    .orElseThrow(() -> new RuntimeException("Restaurant not found"));
            item.setRestaurant(restaurant);
        }

        menuItemRepository.save(item);
        return mapToResponse(item);
    }

    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }

    private MenuItemResponse mapToResponse(MenuItem item) {
        return new MenuItemResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getImage(),
                item.isAvailable(),
                item.getPrice(),
                item.getCategory() != null ? item.getCategory().getId() : null,
                item.getRestaurant() != null ? item.getRestaurant().getId() : null
        );
    }
}
