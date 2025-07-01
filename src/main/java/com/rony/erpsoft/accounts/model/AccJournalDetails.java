package com.rony.erpsoft.accounts.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "acc_journal_Details")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AccJournalDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Organization ID cannot be null")
    @Column(name = "organization_id")
    private Long organizationId;

//    @NotNull(message = "Journal Master ID cannot be null")
    @Column(name="journal_master_id")
    private Long journalMasterId;

/*    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "journal_master_id")
    private AccJournalMaster accJournalMaster;*/

    @Column(name = "row")
    private int row;
    @Column(name = "chart_of_accounts_id")
    private Long chartOfAccountsId;         // id fetch from AccChartOfAccounts domain
    @Column(name = "chart_of_accounts_code_name")
    private String chartOfAccountsCodeName;     // 100001, ....
    @Column(name = "chart_of_accounts_type")
    private String chartOfAccountsType;     // Asset,Liability,Expenditure,Income
    @Column(name = "chart_of_accounts_usage")
    private String chartOfAccountsUsage;    // Cash,Bank,Ledger,AP,AR
    @Column(name = "chart_of_accounts_source")
    private String chartOfAccountsSource;   // None,Subaccount,Customer,Supplier,Employee
    @Column(name = "sub_accounts_id")
    private Long subAccountsId;             // id fetch from AccSubAccounts domain
    @Column(name = "sub_accounts_code_name")
    private String subAccountsCodeName;         // 10000101 or CUS-000004 or SUP-000002

    @Column(name = "debit_credit_flag")
    private String debitCreditFlag;                    // Dr, Cr
    @Column(name = "particulars")
    private String particulars;
    @Column(name = "currency_type")
    private String currencyType;            // BDT, USD
    @Column(name = "currency_rate")
    private double currencyRate;
    @Column(name = "prime_amount")
    private double primeAmount;         // Debit=baseAmount*(1), Credit=baseAmount*(-1)
    @Column(name = "base_amount")
    private double baseAmount;          // currencyRate * primeAmount; Debit=baseAmount*(1), Credit=baseAmount*(-1)
    @Column(name = "amount")
    private double amount;              // absolute amount; no negative figure

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "create_on")
    private Date createOn;
    @Column(name = "updated_by")
    private Long updatedBy;
    @Column(name = "updated_on")
    private Date updatedOn;
}
