package com.order.order.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.order.order.config.RabbitConfig;
import com.order.order.entity.Order;

@Service
public class OrderProducer {

    private final RabbitTemplate rabbitTemplate;

    public OrderProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrder(Order order){
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE, order);
    }
    
}
