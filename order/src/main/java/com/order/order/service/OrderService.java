package com.order.order.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.order.order.client.ProductClient;
import com.order.order.client.UserClient;
import com.order.order.dto.OrderRequest;
import com.order.order.dto.Product;
import com.order.order.dto.UserDto;
import com.order.order.entity.Order;
import com.order.order.entity.PaymentStatusEnum;
import com.order.order.exc.AuthException;
import com.order.order.exc.ResourceNotFoundException;
import com.order.order.mapper.OrderMapper;
import com.order.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final UserClient userClient;
    private final OrderMapper orderMapper;
   

    public Product getProductById(Long productId) {
        log.info("Getting product by id: {}", productId);
        return productClient.getProductById(productId);
    }

    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        Order order = orderMapper.toOrder(orderRequest);
        log.info("Creating order: {}", order);
        
        // Extract username from security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication object: {}", authentication);
        
        if (authentication == null || authentication.getPrincipal() == null) {
            log.error("Authentication is null or principal is null");
            throw new AuthException("User not authenticated");
        }
        
        String username = authentication.getName();
        log.info("Authentication name: {}", username);
        
        if (username == null || username.isEmpty()) {
            log.error("Username is null or empty");
            throw new AuthException("Username not found in token");
        }
        
        if ("anonymousUser".equals(username)) {
            log.error("User is anonymous - JWT token may be missing, invalid, or expired");
            throw new AuthException("User not properly authenticated. Please provide a valid JWT token in the Authorization header.");
        }
        
        log.info("Current username: {}", username);
        
        // Get user details by username
        UserDto user = userClient.getUserByUsername(username);
        if (user == null || user.getId() == null) {
            throw new ResourceNotFoundException("User not found for username: " + username);
        }
        
        Long userId = user.getId();
        log.info("User ID for username '{}': {}", username, userId);
        
        order.setUserId(userId);
        order.setPaymentStatus(PaymentStatusEnum.PENDING);
        
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
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setPaymentStatus(paymentStatus);
        orderRepository.save(order);
    }

    public List<Order> getOrdersByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new AuthException("User not authenticated");
        }
        String username = authentication.getName();
        UserDto user = userClient.getUserByUsername(username);
        Long userId = user.getId();
        log.info("Getting orders by user ID: {}", userId);

        List<Order> orders = orderRepository.findByUserId(userId);
        if (orders == null || orders.isEmpty()) {
            throw new ResourceNotFoundException("No orders found for user ID: " + userId);
        }
        return orders;
    }

    public List<Order> getAllOrders() {
        log.info("Getting all orders");
        return orderRepository.findAll();
    }
}
