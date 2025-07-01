package com.rony.erpsoft.inventory.repository;

import com.rony.erpsoft.inventory.model.InventoryTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryTransferRepository extends JpaRepository<InventoryTransfer, Long> {
}
