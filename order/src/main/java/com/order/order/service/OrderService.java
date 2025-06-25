package com.order.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.order.order.client.ProductClient;
import com.order.order.client.UserClient;
import com.order.order.dto.Product;
import com.order.order.entity.Order;
import com.order.order.entity.PaymentStatusEnum;
import com.order.order.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final UserClient userClient;
    public OrderService(OrderRepository orderRepository, ProductClient productClient, UserClient userClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
        this.userClient = userClient;
    }

    public Product getProductById(Long productId) {
        log.info("Getting product by id: {}", productId);
        return productClient.getProductById(productId);
    }

    @Transactional
    public Order createOrder(Order order) {
        log.info("Creating order: {}", order);
        Long userId = userClient.getUserById(order.getUserId()).getId();
        if (userId == null || userId == 0) {
            throw new RuntimeException("User not found");
        }
        order.setUserId(userId);
        // Decrease product stock
        productClient.decreaseStock(order.getProductId(), order.getQuantity());
        // Save the order
        return orderRepository.save(order);
    }

    public boolean isOrderExist(Long orderId) {
        return orderRepository.existsById(orderId);
    }

    public void updateOrderPaymentStatus(Long orderId, PaymentStatusEnum paymentStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setPaymentStatus(paymentStatus);
        orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        log.info("Getting all orders");
        return orderRepository.findAll();
    }
}
