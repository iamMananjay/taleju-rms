package com.taleju.rms.controller;

import com.taleju.rms.dto.SubscriptionRequest;
import com.taleju.rms.dto.SubscriptionResponse;
import com.taleju.rms.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@PreAuthorize("hasRole('ADMIN')")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionResponse> create(@RequestBody SubscriptionRequest request){
        return ResponseEntity.ok(subscriptionService.createSubscription(request));
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionResponse>> getAll(){
        return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionResponse> update(@PathVariable Long id, @RequestBody SubscriptionRequest request){
        return ResponseEntity.ok(subscriptionService.updateSubscription(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.ok().build();
    }
}

