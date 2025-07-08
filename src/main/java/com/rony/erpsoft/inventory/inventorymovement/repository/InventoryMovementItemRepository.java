package com.rony.erpsoft.inventory.inventorymovement.repository;

import com.rony.erpsoft.inventory.inventorymovement.model.InventoryMovementItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryMovementItemRepository extends JpaRepository<InventoryMovementItem, Long> {
}
