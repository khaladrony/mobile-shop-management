package com.rony.erpsoft.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesCostDTO {
    private LocalDateTime transactionDate;
    private BigDecimal totalSales;
    private BigDecimal purchaseCost;
}
