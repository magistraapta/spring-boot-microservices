package com.order.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
