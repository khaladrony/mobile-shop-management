package com.rony.erpsoft.inventory.inventorymovement.dto;

import com.rony.erpsoft.inventory.enums.InventoryAction;
import com.rony.erpsoft.inventory.enums.InventoryStatus;
import com.rony.erpsoft.inventory.inventorymovement.model.InventoryMovementItem;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class InventoryMovementResponseDTO {
    private Long id;
    private Long organizationId;
    private String transactionId;
    private LocalDateTime transactionDate;
    private String reference;
    private String warehouse;
    private String fromWarehouse;
    private String toWarehouse;
    private int sign;
    private InventoryAction action;
    private int year;
    private int month;
    private InventoryStatus status;
    private String remarks;
    String supplierCode;
    String customerCode;
    private Long createdBy;
    private Long updatedBy;

    private List<InventoryMovementItemResponseDTO> details = new ArrayList<>();
}
