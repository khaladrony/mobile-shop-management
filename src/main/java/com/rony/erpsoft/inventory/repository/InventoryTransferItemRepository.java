package com.rony.erpsoft.inventory.repository;

import com.rony.erpsoft.inventory.model.InventoryTransferItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryTransferItemRepository extends JpaRepository<InventoryTransferItem, Long> {
}
