package com.taleju.rms.controller;

import com.taleju.rms.dto.TableRequest;
import com.taleju.rms.dto.TableResponse;
import com.taleju.rms.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class TableController {
    @Autowired
    private TableService tableService;

    // Create a new table
    @PostMapping
    public ResponseEntity<TableResponse> createTable(@RequestBody TableRequest request) {
        return ResponseEntity.ok(tableService.createTable(request));
    }

    // Get all tables by restaurant
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<TableResponse>> getTablesByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(tableService.getTablesByRestaurant(restaurantId));
    }

    // Get table by ID
    @GetMapping("/id")
    public ResponseEntity<TableResponse> getTableById(@RequestParam String tableNumber) {
        return ResponseEntity.ok(tableService.getTableById(tableNumber));
    }

    // Update table number
    @PutMapping("/{id}")
    public ResponseEntity<TableResponse> updateTableNumber(@PathVariable Long id,
                                                           @RequestBody TableRequest request) {
        return ResponseEntity.ok(tableService.updateTable(id, request.getTableNumber()));
    }

    // Delete table
    @DeleteMapping("/id")
    public ResponseEntity<String> deleteTable(@RequestParam String tableNumber) {
        tableService.deleteTable(tableNumber);
        return ResponseEntity.ok("Table deleted successfully");
    }


}
