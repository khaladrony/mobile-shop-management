package com.rony.erpsoft.inventory.inventorymovement.service;

import com.rony.erpsoft.inventory.enums.InventoryAction;
import com.rony.erpsoft.inventory.enums.InventoryStatus;
import com.rony.erpsoft.inventory.inventorymovement.model.InventoryTransaction;
import com.rony.erpsoft.inventory.inventorymovement.repository.InventoryMovementItemRepository;
import com.rony.erpsoft.inventory.inventorymovement.repository.InventoryMovementRepository;
import com.rony.erpsoft.inventory.inventorymovement.repository.InventoryTransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class InventoryUpdateService {

    private final InventoryMovementRepository inventoryMovementRepository;
    private final InventoryMovementItemRepository inventoryMovementItemRepository;
    private final InventoryTransactionRepository inventoryTransactionRepository;

    private List<InventoryTransaction> inventoryTransactionsSave(List<InventoryTransaction> inventoryTransactions) {
        return inventoryTransactionRepository.saveAll(inventoryTransactions);
    }

    private void inventoryMovementUpdate(Long id) {
        inventoryMovementRepository.updateStatusById(id, InventoryStatus.DELIVERED);
    }

    private void inventoryMovementItemUpdate(List<InventoryTransaction> inventoryTransactions) {
        inventoryTransactions.forEach(inventoryTransaction -> {
            if (inventoryTransaction.getDocumentNo().startsWith("TO")
                    && inventoryTransaction.getAction().equals(InventoryAction.RECEIPT)) {
                inventoryMovementItemRepository.updateDocumentIdById(
                        inventoryTransaction.getDocumentDetailId(), inventoryTransaction.getId());
            } else {
                inventoryMovementItemRepository.updateInventoryTransactionIdById(
                        inventoryTransaction.getDocumentDetailId(), inventoryTransaction.getId());
            }
        });
    }

    @Transactional
    public void dbUpdate(Long id, List<InventoryTransaction> inventoryTransactions) {
        try {
            inventoryTransactions = inventoryTransactionsSave(inventoryTransactions);
            inventoryMovementUpdate(id);
            inventoryMovementItemUpdate(inventoryTransactions);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
