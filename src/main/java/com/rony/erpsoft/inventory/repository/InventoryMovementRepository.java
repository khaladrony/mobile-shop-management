package com.rony.erpsoft.inventory.repository;

import com.rony.erpsoft.inventory.model.InventoryMovement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Long> {
}
