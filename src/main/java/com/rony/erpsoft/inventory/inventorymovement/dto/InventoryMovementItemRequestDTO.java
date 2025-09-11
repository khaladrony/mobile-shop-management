package com.rony.erpsoft.inventory.inventorymovement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class InventoryMovementItemRequestDTO implements Serializable {
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
    private List<ImeiDTO> imeiNumbers = new ArrayList<>();
    private Long createdBy;
    private Long updatedBy;
}
