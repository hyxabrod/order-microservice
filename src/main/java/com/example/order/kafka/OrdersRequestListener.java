package com.example.order.kafka;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.contracts.orders.OrderReply;
import com.example.contracts.orders.OrderRequest;
import com.example.order.service.ProductAvailabilityService;

@Component
public class OrdersRequestListener {

    private static final String REPLY_TOPIC = "orders.reply";

    private final KafkaTemplate<String, OrderReply> replyKafkaTemplate;
    private final ProductAvailabilityService productAvailabilityService;

    public OrdersRequestListener(
            @Qualifier("orderReplyKafkaTemplate") KafkaTemplate<String, OrderReply> replyKafkaTemplate,
            ProductAvailabilityService productAvailabilityService
    ) {
        this.replyKafkaTemplate = replyKafkaTemplate;
        this.productAvailabilityService = productAvailabilityService;
    }

    @KafkaListener(
            topics = "orders.request",
            containerFactory = "orderRequestListenerContainerFactory"
    )
    public void onRequest(ConsumerRecord<String, OrderRequest> record) {
        OrderRequest request = record.value();

        boolean available = productAvailabilityService.isAvailable(request.productId());

        OrderReply reply;
        if (available) {
            int orderId = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);

            reply = OrderReply.ok(request.requestId(), orderId);
        } else {
            reply = OrderReply.error(request.requestId(), "PE001");
        }

        replyKafkaTemplate.send(
                REPLY_TOPIC,
                java.util.Objects.requireNonNull(record.key(), "Kafka message key must not be null"),
                reply
        );
    }
}
