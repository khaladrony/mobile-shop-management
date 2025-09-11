package com.rony.erpsoft.inventory.inventorymovement.service;

import com.rony.erpsoft.application_common.service.GeneralInfoCommonService;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.inventory.enums.InventoryAction;
import com.rony.erpsoft.inventory.inventorymovement.model.InventoryMovement;
import com.rony.erpsoft.inventory.inventorymovement.model.InventoryMovementItem;
import com.rony.erpsoft.inventory.inventorymovement.model.InventoryTransaction;
import com.rony.erpsoft.inventory.inventorymovement.repository.InventoryMovementRepository;
import com.rony.erpsoft.inventory.inventorymovement.repository.InventoryTransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@AllArgsConstructor
public class InventoryMovementPostingService {

    private final InventoryMovementRepository inventoryMovementRepository;
    private final InventoryTransactionRepository inventoryTransactionRepository;
    private final GeneralInfoCommonService generalInfoCommonService;
    private final InventoryUpdateService inventoryUpdateService;


    public AppResponse inventoryPosting(Map<String, Object> request) {
        try {
            JSONArray inventoryMovementIds = new JSONArray(request.get("inventoryMovementIds").toString());

            for (int i = 0; i < inventoryMovementIds.length(); i++) {
                Long inventoryMovementId = inventoryMovementIds.getLong(i);

                List<InventoryTransaction> inventoryTransactions = prepareInventoryTransaction(inventoryMovementId);

                inventoryUpdateService.dbUpdate(inventoryMovementId, inventoryTransactions);
            }

            return AppResponse.build(HttpStatus.OK).body(1);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    private List<InventoryTransaction> prepareInventoryTransaction(long id) {
        AtomicInteger counter = new AtomicInteger(1);
        List<InventoryTransaction> inventoryTransactions = new ArrayList<>();

        String lastIssueId = null;
        String lastReceiptId = null;
        String lastId = null;

        Optional<InventoryMovement> inventoryMovementOptional = inventoryMovementRepository.findById(id);

        if (inventoryMovementOptional.get().getAction().equals(InventoryAction.TRANSFER)) {
            lastIssueId = inventoryTransactionRepository.findLastTransactionId(InventoryAction.ISSUE.getSign());
            lastReceiptId = inventoryTransactionRepository.findLastTransactionId(InventoryAction.RECEIPT.getSign());
        } else {
            lastId = inventoryTransactionRepository.findLastTransactionId(inventoryMovementOptional.get().getAction().getSign());
        }


        for (InventoryMovementItem movementMovementItem : inventoryMovementOptional.get().getDetails()) {

            if (inventoryMovementOptional.get().getAction().equals(InventoryAction.TRANSFER)) {
                String issueId = generateNextTransactionId(InventoryAction.ISSUE, lastIssueId);
                String receiptId = generateNextTransactionId(InventoryAction.RECEIPT, lastReceiptId);

                inventoryTransactions.add(
                        getInventoryTransactionObj(
                                inventoryMovementOptional.get(),
                                movementMovementItem,
                                counter,
                                InventoryAction.ISSUE,
                                inventoryMovementOptional.get().getFromWarehouse(),
                                issueId
                        )
                );
                inventoryTransactions.add(
                        getInventoryTransactionObj(
                                inventoryMovementOptional.get(),
                                movementMovementItem,
                                counter,
                                InventoryAction.RECEIPT,
                                inventoryMovementOptional.get().getToWarehouse(),
                                receiptId
                        )
                );

                lastIssueId = issueId;
                lastReceiptId = receiptId;
            } else {

                String transactionId = generateNextTransactionId(inventoryMovementOptional.get().getAction(), lastId);

                inventoryTransactions.add(
                        getInventoryTransactionObj(
                                inventoryMovementOptional.get(),
                                movementMovementItem,
                                counter,
                                inventoryMovementOptional.get().getAction(),
                                inventoryMovementOptional.get().getWarehouse(),
                                transactionId
                        )
                );

                lastId = transactionId;
            }
        }

        return inventoryTransactions;
    }

    private InventoryTransaction getInventoryTransactionObj(
            InventoryMovement inventoryMovement,
            InventoryMovementItem item,
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
        inventoryTransaction.setUnit(item.getUnit());
        inventoryTransaction.setQuantity(item.getQuantity());
        inventoryTransaction.setSupplierCode(inventoryMovement.getSupplierCode());
        inventoryTransaction.setCustomerCode(inventoryMovement.getCustomerCode());
        inventoryTransaction.setDocumentId(inventoryMovement.getId());
        inventoryTransaction.setDocumentNo(inventoryMovement.getTransactionId());
        inventoryTransaction.setDocumentDetailId(item.getId());
        inventoryTransaction.setDocumentRow(item.getLineNumber());

        return inventoryTransaction;
    }

    public String generateNextTransactionId(InventoryAction action, String lastTransactionId) {
        String prefix = setPrefix(action);
        int length = 6;

        if (lastTransactionId == null || lastTransactionId.isEmpty()) {
            return prefix + String.format("%0" + length + "d", 1);
        }

        // Extract numeric part
        String numberPart = lastTransactionId.substring(prefix.length());
        int next = Integer.parseInt(numberPart) + 1;

        return prefix + String.format("%0" + length + "d", next);
    }

    private String setPrefix(InventoryAction action) {
        return switch (action) {
            case RECEIPT -> "IMRE";
            case ISSUE -> "IMIS";
            case TRANSFER -> "";
        };
    }
}
