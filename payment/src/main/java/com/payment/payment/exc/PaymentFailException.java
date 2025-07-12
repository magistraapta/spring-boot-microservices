package com.payment.payment.exc;

public class PaymentFailException extends RuntimeException {
    public PaymentFailException(String message) {
        super(message);
    }

    public static PaymentFailException forOrderId(Long orderId) {
        return new PaymentFailException("Payment failed for order id: " + orderId);
    }
}
