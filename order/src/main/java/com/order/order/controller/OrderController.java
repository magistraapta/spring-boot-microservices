package com.order.order.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.order.dto.Product;
import com.order.order.entity.Order;
import com.order.order.entity.PaymentStatusEnum;
import com.order.order.exc.InvalidRequestException;
import com.order.order.mapper.OrderMapper;
import com.order.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        try {
            Product product = orderService.getProductById(order.getProductId());
            if (product == null) {
                throw new InvalidRequestException("Product not found");
            }
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to verify product: " + e.getMessage());
        }

        Order createdOrder = orderService.createOrder(orderMapper.toOrderRequest(order));
        
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/{orderId}")
    public boolean getOrderById(@PathVariable Long orderId) {
        return orderService.isOrderExist(orderId);
    }

    @PutMapping("/{orderId}")
    public void updateOrderPaymentStatus(@PathVariable Long orderId, @RequestBody PaymentStatusEnum paymentStatus) {
        orderService.updateOrderPaymentStatus(orderId, paymentStatus);
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<Order>> getOrdersByUserId() {
        List<Order> orders = orderService.getOrdersByUserId();
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}
