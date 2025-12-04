package com.taleju.rms.service;

import com.taleju.rms.dto.TableRequest;
import com.taleju.rms.dto.TableResponse;
import com.taleju.rms.entity.Restaurant;
import com.taleju.rms.entity.TableEntity;
import com.taleju.rms.repository.RestaurantRepository;
import com.taleju.rms.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {

    @Autowired
    private TableRepository tableRepo;

    @Autowired
    private RestaurantRepository restaurantRepo;

    // Create table
    public TableResponse createTable(TableRequest request) {

        Restaurant restaurant = restaurantRepo.findById(request.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        TableEntity table = new TableEntity();
        table.setTableNumber(request.getTableNumber());


        table.setRestaurant(restaurant);

        TableEntity saved = tableRepo.save(table);

        return mapToResponse(saved);
    }

    // Get tables by restaurant
    public List<TableResponse> getTablesByRestaurant(Long restaurantId) {
        return tableRepo.findByRestaurantId(restaurantId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Get table by ID
    public TableResponse getTableById(String tableNumber) {
        TableEntity table = tableRepo.findByTableNumber(tableNumber)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        return mapToResponse(table);
    }

    // Update table number
    public TableResponse updateTable(Long id, String newTableNumber) {
        TableEntity table = tableRepo.findByTableNumber(new String(String.valueOf(id)))
                .orElseThrow(() -> new RuntimeException("Table not found"));

        table.setTableNumber(newTableNumber);


        TableEntity saved = tableRepo.save(table);
        return mapToResponse(saved);
    }

    // Delete table
    public void deleteTable(String tableNumber) {
        TableEntity table = tableRepo.findByTableNumber(tableNumber)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        tableRepo.delete(table);
    }


    // Helper mapper
    private TableResponse mapToResponse(TableEntity t) {
        TableResponse r = new TableResponse();
        r.setId(t.getId());
        r.setTableNumber(t.getTableNumber());
        r.setRestaurantID(t.getRestaurant().getId());
        return r;
    }
}
