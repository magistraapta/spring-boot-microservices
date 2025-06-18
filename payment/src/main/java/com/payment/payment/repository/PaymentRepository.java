package com.payment.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
