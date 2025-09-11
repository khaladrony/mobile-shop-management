package com.rony.erpsoft.inventory.inventorymovement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class InventoryMovementItemResponseDTO {
    private Long id;
    private Long organizationId;
    private Long inventoryMovementId;
    private int lineNumber;
    private String itemCode;
    private String itemNameCode;
    private String unit;
    private BigDecimal quantity;
    private BigDecimal rate;
    private BigDecimal value;
    private BigDecimal quantityConfirm;
    private Long InventoryTransactionId;
    private List<ImeiDTO> imeiNumbers = new ArrayList<>();
    private Long createdBy;
    private Long updatedBy;
}
