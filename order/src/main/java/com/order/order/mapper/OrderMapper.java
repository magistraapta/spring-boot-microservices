package com.order.order.mapper;

import org.springframework.stereotype.Component;

import com.order.order.dto.OrderRequest;
import com.order.order.entity.Order;

@Component
public class OrderMapper {
    
    public Order toOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setProductId(orderRequest.getProductId());
        order.setQuantity(orderRequest.getQuantity());
        return order;
    }

    public OrderRequest toOrderRequest(Order order) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setProductId(order.getProductId());
        orderRequest.setQuantity(order.getQuantity());
        return orderRequest;
    }
}
