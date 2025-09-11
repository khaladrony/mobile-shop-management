package com.rony.erpsoft.sales.model;

import com.rony.erpsoft.application_common.model.BaseEntity;
import com.rony.erpsoft.sales.model.enums.DiscountType;
import com.rony.erpsoft.sales.model.enums.PaymentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "order_default_Setup")
@Getter
@Setter
@NoArgsConstructor
public class OrderDefaultSetup extends BaseEntity {

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "vat_rate")
    private BigDecimal vatRate = BigDecimal.ZERO;

    @Column(name = "service_charge_rate")
    private BigDecimal serviceChargeRate = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type")
    private DiscountType discountType = DiscountType.NONE;

    @Column(name = "max_discount_limit")
    private BigDecimal maxDiscountLimit = BigDecimal.ZERO;

    @Column(name = "currency_code")
    private String currencyCode = "BDT";

    @Column(name = "allow_credit_sale")
    private Boolean allowCreditSale = false;

    @Column(name = "print_footer_note")
    private String printFooterNote;

    @Enumerated(EnumType.STRING)
    @Column(name = "rounding_mode")
    private RoundingMode roundingMode = RoundingMode.HALF_UP;

    @Column(name = "auto_print")
    private Boolean autoPrint = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "default_payment_type")
    private PaymentType defaultPaymentType = PaymentType.CASH;

    @Column(name = "enable_multi_payment")
    private Boolean enableMultiPayment = false;

    @Column(name = "receipt_copy_count")
    private Integer receiptCopyCount = 1;
}
