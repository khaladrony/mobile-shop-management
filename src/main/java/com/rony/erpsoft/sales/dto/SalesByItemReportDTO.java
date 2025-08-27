package com.rony.erpsoft.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesByItemReportDTO {
    private String itemCode;
    private String itemName;
    private Long totalQuantity;
    private BigDecimal totalSales;
}
