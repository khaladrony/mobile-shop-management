package com.rony.erpsoft.inventory.inventorymovement.dto;

import com.rony.erpsoft.inventory.enums.InventoryAction;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InventoryTransactionDTO {

    private Long id;
    private String transactionId;
    private LocalDateTime transactionDate;
    private String warehouse;
    private int sign;
    private InventoryAction action;
    private int year;
    private int month;
    private String itemCode;
    private int lineNumber;
    private String unit;
    private BigDecimal quantity;
    private BigDecimal value;
    private String supplierCode;
    private String customerCode;
    private String documentNo;
    private Long documentId;
    private Long documentDetailId;
    private int documentRow;
}
