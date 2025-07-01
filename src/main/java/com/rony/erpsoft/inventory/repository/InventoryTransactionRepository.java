package com.rony.erpsoft.inventory.repository;

import com.rony.erpsoft.inventory.model.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {
}
