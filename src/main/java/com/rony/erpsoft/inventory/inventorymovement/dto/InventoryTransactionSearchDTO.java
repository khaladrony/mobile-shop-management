package com.rony.erpsoft.inventory.inventorymovement.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryTransactionSearchDTO {

    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String warehouse;
    private String itemCode;
    private String reportType;
}
