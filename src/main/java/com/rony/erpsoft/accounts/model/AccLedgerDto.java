package com.rony.erpsoft.accounts.model;


import java.util.Date;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(String voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getMasterParticulars() {
        return masterParticulars;
    }

    public void setMasterParticulars(String masterParticulars) {
        this.masterParticulars = masterParticulars;
    }

    public Long getChartOfAccountsId() {
        return chartOfAccountsId;
    }

    public void setChartOfAccountsId(Long chartOfAccountsId) {
        this.chartOfAccountsId = chartOfAccountsId;
    }

    public String getChartOfAccountsCodeName() {
        return chartOfAccountsCodeName;
    }

    public void setChartOfAccountsCodeName(String chartOfAccountsCodeName) {
        this.chartOfAccountsCodeName = chartOfAccountsCodeName;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public double getPrimeAmount() {
        return primeAmount;
    }

    public void setPrimeAmount(double primeAmount) {
        this.primeAmount = primeAmount;
    }

    public double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    public Long getSubAccountsId() {
        return subAccountsId;
    }

    public void setSubAccountsId(Long subAccountsId) {
        this.subAccountsId = subAccountsId;
    }

    public String getChartOfAccountsType() {
        return chartOfAccountsType;
    }

    public void setChartOfAccountsType(String chartOfAccountsType) {
        this.chartOfAccountsType = chartOfAccountsType;
    }

    public String getChartOfAccountsUsage() {
        return chartOfAccountsUsage;
    }

    public void setChartOfAccountsUsage(String chartOfAccountsUsage) {
        this.chartOfAccountsUsage = chartOfAccountsUsage;
    }

    public String getChartOfAccountsSource() {
        return chartOfAccountsSource;
    }

    public void setChartOfAccountsSource(String chartOfAccountsSource) {
        this.chartOfAccountsSource = chartOfAccountsSource;
    }

    public String getSubAccountsCodeName() {
        return subAccountsCodeName;
    }

    public void setSubAccountsCodeName(String subAccountsCodeName) {
        this.subAccountsCodeName = subAccountsCodeName;
    }

    public String getAccountsCode() {
        return accountsCode;
    }

    public void setAccountsCode(String accountsCode) {
        this.accountsCode = accountsCode;
    }

    public String getAccountsName() {
        return accountsName;
    }

    public void setAccountsName(String accountsName) {
        this.accountsName = accountsName;
    }
}
