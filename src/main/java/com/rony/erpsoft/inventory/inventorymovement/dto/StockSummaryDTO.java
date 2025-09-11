package com.rony.erpsoft.inventory.inventorymovement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockSummaryDTO {

    private String itemCode;
    private String itemName;
    private String unit;
    private String warehouse;

    private BigDecimal openingQty;
    private BigDecimal receiptQty;
    private BigDecimal issueQty;
    private BigDecimal closingQty;
}
