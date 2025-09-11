package com.rony.erpsoft.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemSummaryDTO {
    private String itemName;
    private int totalQuantity;
    private BigDecimal price;
    private BigDecimal totalGrossAmount;
    private BigDecimal totalDiscountAmount;
    private BigDecimal totalNetAmount;
    private BigDecimal totalVatAmount;
    private BigDecimal totalAmount;
}