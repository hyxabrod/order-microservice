package com.example.order;

public record OrderDto(
        Integer productId,
        Integer userId
        ) {

    public static OrderDto fromEntity(Order order) {
        return new OrderDto(
                order.getProductId(),
                order.getUserId()
        );
    }
}
