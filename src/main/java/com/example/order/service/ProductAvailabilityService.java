package com.example.order.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ProductAvailabilityService {

    public boolean isAvailable(UUID productId) {
        return true;
    }
}
