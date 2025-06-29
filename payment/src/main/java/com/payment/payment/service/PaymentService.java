package com.payment.payment.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.payment.payment.client.OrderClient;
import com.payment.payment.dto.PaymentRequest;
import com.payment.payment.dto.UserDto;
import com.payment.payment.entity.Payment;
import com.payment.payment.entity.PaymentStatusEnum;
import com.payment.payment.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;

    public PaymentService(PaymentRepository paymentRepository, OrderClient orderClient) {
        this.paymentRepository = paymentRepository;
        this.orderClient = orderClient;
    }

    public Payment createPayment(Long orderId, PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());

        if (!orderClient.isOrderExist(orderId)) {
            throw new RuntimeException("Order not found");
        }

        if (paymentRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        orderClient.updateOrderPaymentStatus(orderId, PaymentStatusEnum.PAID);

        return paymentRepository.save(payment);
    }

    public Payment createPaymentFromOrder(Long orderId, Long userId, BigDecimal amount, String paymentMethod) {
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setUserId(userId);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        
        // Set timestamps
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        payment.setCreatedAt(now);
        payment.setUpdatedAt(now);

        // Validate order exists
        if (!orderClient.isOrderExist(orderId)) {
            throw new RuntimeException("Order not found");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        // Update order payment status
        orderClient.updateOrderPaymentStatus(orderId, PaymentStatusEnum.PAID);

        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    
    
}
