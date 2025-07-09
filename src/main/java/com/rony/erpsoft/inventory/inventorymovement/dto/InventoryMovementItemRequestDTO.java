package com.rony.erpsoft.inventory.inventorymovement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class InventoryMovementItemRequestDTO {
    private Long id;
    private Long organizationId;
    private Long inventoryMovementId;
    private int lineNumber;
    private String itemCode;
    private String unit;
    private BigDecimal quantity;
    private BigDecimal rate;
    private BigDecimal value;
    private BigDecimal quantityConfirm;
    private Long InventoryTransactionId;
    private Long createdBy;
    private Long updatedBy;
}
