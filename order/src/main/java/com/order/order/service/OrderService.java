package com.order.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.order.order.client.ProductClient;
import com.order.order.entity.Order;
import com.order.order.repository.OrderRepository;

@Service
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public OrderService(OrderRepository orderRepository, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
    }

    public com.order.order.client.Product getProductById(Long productId) {
        return productClient.getProductById(productId);
    }

    @Transactional
    public Order createOrder(Order order) {
        // Decrease product stock
        productClient.decreaseStock(order.getProductId(), order.getQuantity());
        
        // Save the order
        return orderRepository.save(order);
    }   

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
