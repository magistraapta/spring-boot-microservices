package com.order.order.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.order.client.Product;
import com.order.order.entity.Order;
import com.order.order.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        try {
            // Validate order data
            if (order.getProductId() == null || order.getQuantity() == null) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Product ID and quantity are required"
                ));
            }
            
            if (order.getQuantity() <= 0) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Quantity must be greater than 0"
                ));
            }

            // Verify product exists before creating order
            try {
                Product product = orderService.getProductById(order.getProductId());
                if (product == null) {
                    return ResponseEntity.badRequest().body(Map.of(
                        "error", "Product not found"
                    ));
                }
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Failed to verify product: " + e.getMessage()
                ));
            }

            Order createdOrder = orderService.createOrder(order);
            return ResponseEntity.ok(Map.of(
                "message", "Order created successfully",
                "order", createdOrder
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Failed to create order: " + e.getMessage()
            ));
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}
