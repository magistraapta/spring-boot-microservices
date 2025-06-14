package com.inventory.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.inventory.entity.InventoryEntity;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
    boolean existsBySkuCodeAndQuantityGreaterThanEqual(String skuCode, Integer quantity);
}
