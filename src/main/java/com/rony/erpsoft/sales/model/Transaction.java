package com.rony.erpsoft.sales.model;

import com.rony.erpsoft.application_common.model.BaseEntity;
import com.rony.erpsoft.sales.model.enums.POSTransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction extends BaseEntity {

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    private POSTransactionType type; // INCOME or EXPENSE

    private String category; // SALES, SALARY, RENT, etc.

    private BigDecimal amount;

    @Column(name = "reference_id")
    private String referenceId; // e.g., Order ID, Vendor Invoice

    private String remarks;

    @Column(name = "branch_code")
    private String branchCode;
}
