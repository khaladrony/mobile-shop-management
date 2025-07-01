package com.rony.erpsoft.accounts.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AccLedgerDto {
    private long id;
    private String voucherNo;
    private String voucherDate;
    private String masterParticulars;

    private Long chartOfAccountsId;
    private String accountsCode;
    private String accountsName;
    private String chartOfAccountsCodeName;
    private Long subAccountsId;             // id fetch from AccSubAccounts domain
    private String subAccountsCodeName;
    private String chartOfAccountsType;     // Asset,Liability,Expenditure,Income
    private String chartOfAccountsUsage;    // Cash,Bank,Ledger,AP,AR
    private String chartOfAccountsSource;   // None,Subaccount,Customer,Supplier,Employee
    private String particulars;
    private double primeAmount;
    private double debitAmount;
    private double creditAmount;
    private double balanceAmount;
    private Date postingDate;

}
