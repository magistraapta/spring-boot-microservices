package com.payment.payment.consumer;

import java.math.BigDecimal;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.payment.payment.client.ProductClient;
import com.payment.payment.dto.OrderDto;
import com.payment.payment.dto.ProductResponse;
import com.payment.payment.entity.Payment;
import com.payment.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderConsumer {

    private final PaymentService paymentService;
    private final ProductClient productClient;

    @RabbitListener(queues = "order-queue")
    public void processOrder(OrderDto orderDto) {
        log.info("Received order from queue: {}", orderDto.getId());
        
        try {
            // Calculate amount based on product price and quantity
            BigDecimal amount = calculateOrderAmount(orderDto);
            
            // Create payment using the service
            Payment savedPayment = paymentService.createPaymentFromOrder(
                orderDto.getId(), 
                orderDto.getUserId(), 
                amount, 
                "CREDIT_CARD"
            );
            
            log.info("Payment created successfully for order {}: {}", orderDto.getId(), savedPayment.getId());
            
        } catch (Exception e) {
            log.error("Error processing order {}: {}", orderDto.getId(), e.getMessage(), e);
            // In a production environment, you might want to send to a dead letter queue
            // or implement retry logic
        }
    }
    
    private BigDecimal calculateOrderAmount(OrderDto orderDto) {
        try {
            // Fetch product information from product service
            ProductResponse product = productClient.getProductById(orderDto.getProductId());
            
            if (product == null || product.getPrice() == null) {
                log.warn("Product not found or price is null for product ID: {}", orderDto.getProductId());
                // Fallback to default price
                BigDecimal unitPrice = new BigDecimal("10.00");
                return unitPrice.multiply(new BigDecimal(orderDto.getQuantity()));
            }
            
            // Calculate total amount: price * quantity
            BigDecimal unitPrice = BigDecimal.valueOf(product.getPrice());
            return unitPrice.multiply(new BigDecimal(orderDto.getQuantity()));
            
        } catch (Exception e) {
            log.error("Error fetching product information for product ID {}: {}", 
                     orderDto.getProductId(), e.getMessage());
            // Fallback to default price
            BigDecimal unitPrice = new BigDecimal("10.00");
            return unitPrice.multiply(new BigDecimal(orderDto.getQuantity()));
        }
    }
} 