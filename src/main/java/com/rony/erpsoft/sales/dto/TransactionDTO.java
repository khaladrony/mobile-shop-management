package com.rony.erpsoft.sales.dto;

import com.rony.erpsoft.sales.model.enums.POSTransactionType;
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
public class TransactionDTO {
    private Long id;
    private LocalDateTime transactionDate;
    private POSTransactionType type; // INCOME or EXPENSE
    private String category; // SALES, SALARY, RENT, etc.
    private BigDecimal amount;
    private String referenceId; // e.g., Order ID, Vendor Invoice
    private String remarks;
}
