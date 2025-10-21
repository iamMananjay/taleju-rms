package com.taleju.rms.controller;

import com.taleju.rms.dto.SubscriptionPlanRequest;
import com.taleju.rms.dto.SubscriptionPlanResponse;
import com.taleju.rms.service.SubscriptionPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscription-plans")
@PreAuthorize("hasRole('ADMIN')")
public class SubscriptionPlanController {

    @Autowired
    private SubscriptionPlanService planService;

    @PostMapping
    public ResponseEntity<SubscriptionPlanResponse> create(@RequestBody SubscriptionPlanRequest request){
        return ResponseEntity.ok(planService.createPlan(request));
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionPlanResponse>> getAll(){
        return ResponseEntity.ok(planService.getAllPlans());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionPlanResponse> update(@PathVariable Long id, @RequestBody SubscriptionPlanRequest request){
        return ResponseEntity.ok(planService.updatePlan(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        planService.deletePlan(id);
        return ResponseEntity.ok().build();
    }
}

