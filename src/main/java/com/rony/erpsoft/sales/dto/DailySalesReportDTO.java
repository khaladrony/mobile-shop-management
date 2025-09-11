package com.rony.erpsoft.sales.dto;

import com.rony.erpsoft.sales.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailySalesReportDTO {
    private PaymentType paymentType;
    private Long numberOfInvoices;
    private Long totalQuantity;
    private BigDecimal grossAmount;
    private BigDecimal discountValue;
    private BigDecimal netAmount;
    private BigDecimal vatAmount;
    private BigDecimal totalSales;
}
