package com.rony.erpsoft.inventory.repository;

import com.rony.erpsoft.inventory.model.InventoryMovementItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryMovementItemRepository extends JpaRepository<InventoryMovementItem, Long> {
}
