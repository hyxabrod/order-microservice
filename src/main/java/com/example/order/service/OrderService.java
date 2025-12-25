package com.example.order.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Optional<Order> getOrderById(int id) {
        return repository.findById(id);
    }
}
