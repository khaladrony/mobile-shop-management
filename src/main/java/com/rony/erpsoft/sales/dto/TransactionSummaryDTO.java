package com.rony.erpsoft.sales.dto;

import com.rony.erpsoft.sales.model.enums.POSTransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSummaryDTO {
    private POSTransactionType type; // INCOME or EXPENSE
    private BigDecimal totalAmount;
}
