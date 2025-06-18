package com.payment.payment.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.payment.payment.client.OrderClient;
import com.payment.payment.dto.PaymentRequest;
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

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
    
}
