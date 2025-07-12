package com.payment.payment.exc;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(String message) {
        super(message);
    }

    public static PaymentNotFoundException forId(Long id) {
        return new PaymentNotFoundException("Payment not found with id: " + id);
    }
}
