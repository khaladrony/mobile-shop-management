package com.rony.erpsoft.inventory.inventorymovement.repository;

import com.rony.erpsoft.inventory.inventorymovement.model.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {

    @Query("SELECT max(transactionId) FROM InventoryTransaction WHERE sign=?1")
    String findLastTransactionId(int sign);
}
