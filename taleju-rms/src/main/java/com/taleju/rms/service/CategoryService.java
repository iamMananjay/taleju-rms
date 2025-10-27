package com.taleju.rms.service;

import com.taleju.rms.dto.CategoryRequest;
import com.taleju.rms.dto.CategoryResponse;
import com.taleju.rms.entity.Category;
import com.taleju.rms.entity.Restaurant;
import com.taleju.rms.repository.CategoryRepository;
import com.taleju.rms.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public CategoryResponse createCategory(CategoryRequest request) {
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Category category = new Category();
        category.setName(request.getName());
        category.setRestaurant(restaurant);

        categoryRepository.save(category);
        return mapToResponse(category);
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (request.getName() != null)
            category.setName(request.getName());

        if (request.getRestaurantId() != null) {
            Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                    .orElseThrow(() -> new RuntimeException("Restaurant not found"));
            category.setRestaurant(restaurant);
        }

        categoryRepository.save(category);
        return mapToResponse(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryResponse mapToResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getRestaurant() != null ? category.getRestaurant().getId() : null
        );
    }
}
