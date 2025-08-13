package com.rony.erpsoft.inventory.inventorymovement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rony.erpsoft.inventory.enums.InventoryAction;
import com.rony.erpsoft.inventory.enums.InventoryStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class InventoryMovementRequestDTO implements Serializable {
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

    private List<InventoryMovementItemRequestDTO> details = new ArrayList<>();
}
