package com.rony.erpsoft.accounts.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FinancialAccountDto {
    private Long chartOfAccountsId;
    private String accountsCode;
    private String accountsName;
    private String accountsType;
    private BigDecimal amount;
}
