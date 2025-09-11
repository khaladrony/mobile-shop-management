package com.rony.erpsoft.inventory.inventorymovement.service;

import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.inventory.enums.InventoryAction;
import com.rony.erpsoft.inventory.inventorymovement.model.InventoryTransaction;
import com.rony.erpsoft.inventory.inventorymovement.repository.InventoryTransactionRepository;
import com.rony.erpsoft.sales.dto.OrderItemResponseDTO;
import com.rony.erpsoft.sales.dto.OrderResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@AllArgsConstructor
public class SalesPostingService {

    private final InventoryMovementPostingService inventoryMovementPostingService;
    private final InventoryTransactionRepository inventoryTransactionRepository;

    public AppResponse salesPostingToInventory(OrderResponseDTO responseDTO) {
        try {
            inventoryTransactionRepository.saveAll(prepareInventoryTransaction(responseDTO));
            return AppResponse.build(HttpStatus.OK).body(true);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    private List<InventoryTransaction> prepareInventoryTransaction(OrderResponseDTO responseDTO) {
        String defaultWarehouse = null;
        AtomicInteger counter = new AtomicInteger(1);
        List<InventoryTransaction> inventoryTransactions = new ArrayList<>();
        String lastId = inventoryTransactionRepository.findLastTransactionId(InventoryAction.ISSUE.getSign());


        for (OrderItemResponseDTO item : responseDTO.getItems()) {
            String transactionId = inventoryMovementPostingService.generateNextTransactionId(InventoryAction.ISSUE, lastId);

            inventoryTransactions.add(
                    getInventoryTransactionObj(
                            responseDTO,
                            item,
                            counter,
                            InventoryAction.ISSUE,
                            defaultWarehouse,
                            transactionId
                    )
            );

            lastId = transactionId;
        }

        return inventoryTransactions;
    }

    private InventoryTransaction getInventoryTransactionObj(
            OrderResponseDTO order,
            OrderItemResponseDTO item,
            AtomicInteger counter,
            InventoryAction action,
            String warehouse,
            String transactionId
    ) {

        InventoryTransaction inventoryTransaction = new InventoryTransaction();

        inventoryTransaction.setTransactionId(transactionId);
        inventoryTransaction.setWarehouse(warehouse);
        inventoryTransaction.setSign(action.getSign());
        inventoryTransaction.setAction(action);

        inventoryTransaction.setTransactionDate(LocalDateTime.now());
        inventoryTransaction.setYear(LocalDateTime.now().getYear());
        inventoryTransaction.setMonth(LocalDateTime.now().getMonthValue());
        inventoryTransaction.setItemCode(item.getItemCode());
        inventoryTransaction.setLineNumber(counter.getAndIncrement());
        inventoryTransaction.setUnit(null);
        inventoryTransaction.setQuantity(BigDecimal.valueOf(item.getQuantity()));
        inventoryTransaction.setSupplierCode(null);
        inventoryTransaction.setCustomerCode(null);
        inventoryTransaction.setDocumentId(order.getId());
        inventoryTransaction.setDocumentNo(order.getTransactionId());
        inventoryTransaction.setDocumentDetailId(item.getId());
        inventoryTransaction.setDocumentRow(item.getLineNumber());

        return inventoryTransaction;
    }
}
