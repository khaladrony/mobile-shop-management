package com.rony.erpsoft.inventory.inventorymovement.repository;

import com.rony.erpsoft.inventory.inventorymovement.model.InventoryMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Long> {

    @Query("SELECT max(transactionId) FROM InventoryMovement WHERE sign=?1")
    String findLastTransactionId(int sign);
}
