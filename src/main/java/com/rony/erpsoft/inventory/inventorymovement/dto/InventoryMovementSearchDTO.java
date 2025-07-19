package com.rony.erpsoft.inventory.inventorymovement.dto;

import com.rony.erpsoft.inventory.enums.InventoryAction;
import com.rony.erpsoft.inventory.enums.InventoryStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryMovementSearchDTO {
    private String warehouse;
    private InventoryAction action;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String transactionId;
    private InventoryStatus status;
}
