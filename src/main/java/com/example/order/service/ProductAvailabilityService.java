package com.example.order.service;

import org.springframework.stereotype.Service;

@Service
public class ProductAvailabilityService {

    public boolean isAvailable(int productId) {
        return true;
    }
}
