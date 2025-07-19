package com.rony.erpsoft.inventory.inventorymovement.repository;

import com.rony.erpsoft.inventory.enums.InventoryAction;
import com.rony.erpsoft.inventory.inventorymovement.model.InventoryMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Long>,
        JpaSpecificationExecutor<InventoryMovement> {

    @Query("SELECT max(transactionId) FROM InventoryMovement WHERE sign=?1")
    String findLastTransactionId(int sign);

    @Query("SELECT im.transactionId FROM InventoryMovement im WHERE im.action= :action AND im.transactionId LIKE %:query%")
    List<String> findTransactionIdsByQuery(
            @Param("action") InventoryAction action,
            @Param("query") String query
    );
}
