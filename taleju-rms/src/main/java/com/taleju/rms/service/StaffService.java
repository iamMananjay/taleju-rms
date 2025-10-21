package com.taleju.rms.service;
import com.taleju.rms.dto.StaffRequest;
import com.taleju.rms.dto.StaffResponse;
import com.taleju.rms.entity.Restaurant;
import com.taleju.rms.entity.Role;
import com.taleju.rms.entity.User;
import com.taleju.rms.repository.RestaurantRepository;
import com.taleju.rms.repository.RoleRepository;
import com.taleju.rms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    // Create new staff
    public StaffResponse  createStaff(StaffRequest request) {
        Role role = null;
        if (request.getRole() != null) {
            role = roleRepository.findByName(request.getRole()).orElse(null);
            // If role not found, keep it null
        }
        Restaurant restaurant = null;
        if (request.getRestaurantId() != null) {
            restaurant = restaurantRepository.findById(request.getRestaurantId()).orElse(null);
            // If restaurant not found, keep it null
        }


        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setRestaurant(restaurant);

        userRepository.save(user);
        return new StaffResponse(user.getId(), user.getName(), user.getEmail(), user.getRole() != null ? user.getRole().getName() : null,
                user.getRestaurant() != null ? user.getRestaurant().getId() : null);

    }

    // Get all staff
    public List<StaffResponse> getAllStaff() {
        return userRepository.findAll().stream()
                .map(user -> new StaffResponse(user.getId(), user.getName(), user.getEmail(),
                        user.getRole().getName(),user.getRestaurant() != null ? user.getRestaurant().getId() : null))
                .collect(Collectors.toList());
    }
    // UPDATE staff
    public StaffResponse updateStaff(Long id, StaffRequest dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getPassword() != null) user.setPassword(passwordEncoder.encode(dto.getPassword()));

        if (dto.getRole() != null) {
            Role role = roleRepository.findByName(dto.getRole()).orElse(null); // allow null
            user.setRole(role);
        }
        if (dto.getRestaurantId() != null) {
            Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId()).orElse(null); // allow null
            user.setRestaurant(restaurant);
        }


        userRepository.save(user);

        return new StaffResponse(user.getId(), user.getName(), user.getEmail(),user.getRole() != null ? user.getRole().getName() : null
                , user.getRestaurant() != null ? user.getRestaurant().getId() : null);
    }

    // Delete staff by ID
    public void deleteStaff(Long id) {
        userRepository.deleteById(id);
    }
}
