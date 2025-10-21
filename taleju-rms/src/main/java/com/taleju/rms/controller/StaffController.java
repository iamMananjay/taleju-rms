package com.taleju.rms.controller;

import com.taleju.rms.dto.StaffRequest;
import com.taleju.rms.dto.StaffResponse;
import com.taleju.rms.entity.User;
import com.taleju.rms.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;

    // Create staff (only Admin or Manager)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public ResponseEntity<StaffResponse> createStaff(@RequestBody StaffRequest dto) {
        return ResponseEntity.ok(staffService.createStaff(dto));
    }

    // Get all staff (Admin/Manager)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<StaffResponse>> getAllStaff() {
        return ResponseEntity.ok(staffService.getAllStaff());
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<StaffResponse> updateStaff(@PathVariable Long id, @RequestBody StaffRequest dto) {
        return ResponseEntity.ok(staffService.updateStaff(id, dto));
    }

    // Delete staff
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.ok("Staff deleted successfully");
    }
}
