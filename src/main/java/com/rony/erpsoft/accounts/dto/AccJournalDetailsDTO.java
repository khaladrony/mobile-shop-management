package com.rony.erpsoft.accounts.dto;

import lombok.Data;

@Data
public class AccJournalDetailsDTO {
    private Long id;
    private Long journalMasterId;
    private int row;
    private Long chartOfAccountsId;
    private String chartOfAccountsCodeName;
    private Long subAccountsId;
    private String subAccountsCodeName;
    private String particulars;
    private String debitCreditFlag;
    private double amount;
    private double primeAmount;
}
