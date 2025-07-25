package com.rony.erpsoft.inventory.inventorymovement.repository;

import com.rony.erpsoft.inventory.inventorymovement.model.InventoryMovementItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryMovementItemRepository extends JpaRepository<InventoryMovementItem, Long> {

    @Modifying
    @Query("UPDATE InventoryMovementItem item SET item.inventoryTransactionId = :transactionId WHERE item.id = :id")
    int updateInventoryTransactionIdById(@Param("id") Long id, @Param("transactionId") Long transactionId);

    @Modifying
    @Query("UPDATE InventoryMovementItem item SET item.documentId = :transactionId WHERE item.id = :id")
    int updateDocumentIdById(@Param("id") Long id, @Param("transactionId") Long transactionId);
}
