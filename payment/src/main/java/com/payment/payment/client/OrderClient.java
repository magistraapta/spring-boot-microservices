package com.payment.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.payment.payment.entity.PaymentStatusEnum;

@FeignClient(name = "order-service", url = "http://localhost:8082")
public interface OrderClient {
    @PutMapping("/orders/{orderId}")
    void updateOrderPaymentStatus(@PathVariable Long orderId, @RequestBody PaymentStatusEnum paymentStatus);

    @GetMapping("/orders/{orderId}")
    boolean isOrderExist(@PathVariable Long orderId);
}
