package com.example.order.config;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.lang.NonNull;

import com.example.contracts.orders.OrderReply;
import com.example.contracts.orders.OrderRequest;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Bean
    @NonNull
    public ProducerFactory<String, OrderReply> orderReplyProducerFactory() {
        Map<String, Object> props = new java.util.HashMap<>();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean(name = "orderReplyKafkaTemplate")
    public KafkaTemplate<String, OrderReply> orderReplyKafkaTemplate() {
        return new KafkaTemplate<>(orderReplyProducerFactory());
    }

    @Bean
    @NonNull
    public ConsumerFactory<String, OrderRequest> orderRequestConsumerFactory() {
        JsonDeserializer<OrderRequest> valueDeserializer = new JsonDeserializer<>(OrderRequest.class);
        valueDeserializer.addTrustedPackages("*");
        Map<String, Object> props = new java.util.HashMap<>();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), valueDeserializer);
    }

    @Bean(name = "orderRequestListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, OrderRequest>
            orderRequestListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, OrderRequest>();
        factory.setConsumerFactory(orderRequestConsumerFactory());
        return factory;
    }
}
