package com.rony.erpsoft.sales.dto;

import com.rony.erpsoft.sales.model.enums.DiscountType;
import com.rony.erpsoft.sales.model.enums.PaymentType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDefaultSetupDTO {

    private Long id;
    private String branchCode;
    private BigDecimal vatRate;
    private BigDecimal serviceChargeRate;
    private DiscountType discountType;
    private BigDecimal maxDiscountLimit;
    private String currencyCode;
    private Boolean allowCreditSale;
    private String printFooterNote;
    private RoundingMode roundingMode;
    private Boolean autoPrint = true;
    private PaymentType defaultPaymentType;
    private Boolean enableMultiPayment;
    private Integer receiptCopyCount;
}
