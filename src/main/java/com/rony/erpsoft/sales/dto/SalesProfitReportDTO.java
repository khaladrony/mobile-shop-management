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
public class SalesProfitReportDTO {
    private BigDecimal totalSales;
    private BigDecimal purchaseCost;
    private BigDecimal grossProfit;
    private BigDecimal expenses;
    private BigDecimal salesReturns;
    private BigDecimal netProfit;
}
