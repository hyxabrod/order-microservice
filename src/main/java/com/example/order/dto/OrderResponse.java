package com.example.order.dto;

public class OrderResponse {

    private final Integer productId;
    private final Integer userId;

    public OrderResponse(Integer productId, Integer userId) {
        this.productId = productId;
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getUserId() {
        return userId;
    }
}
