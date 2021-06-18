package com.rony.erpsoft.accounts.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "acc_journal_master")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AccJournalMaster implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Organization ID cannot be null")
    @Column(name = "organization_id")
    private Long organizationId;

    @Column(name = "voucher_no", unique = true)
    private String voucherNo;
    @Column(name = "reference")
    private String reference;
    @Column(name = "voucher_date")
    private Date voucherDate;
    @Column(name = "particulars")
    private String particulars;
    @Column(name = "year")
    private int year;
    @Column(name = "month")
    private int month;
    @Column(name = "status")
    private String status;          // Draft, Submitted, Approved, Posted, Cancelled
    @Column(name = "voucher_type")
    private String voucherType;     // Debit Voucher, Credit Voucher, Journal Voucher, Transfer Voucher
    @Column(name = "payment_type")
    private String paymentType;     // Cash, Bank(bankName, chequeNo, chequeDate), Card, *N/A
    @Column(name = "voucher_prefix")
    private String voucherPrefix;   // JV, CV, DV, TV, OB
    @Column(name = "action")
    private String action;          // Journal, Opening Balance, Receipt, Payment
    @Column(name = "cheque_no")
    private String chequeNo;
    @Column(name = "cheque_date")
    private Date chequeDate;
    @Column(name = "cheque_status")
    private String chequeStatus;    // Not Cleared, Cleared, Bounced
    @Column(name = "bank_account_id")
    private Long bankAccountId;
    @Column(name = "amount")
    private double amount;
    @Column(name = "posting_date")
    private Date postingDate;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "journal_master_id")
    private List<AccJournalDetails> details = new ArrayList<AccJournalDetails>();

    @Transient
    private List<AccJournalDetails> editData = new ArrayList<AccJournalDetails>();

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "create_on")
    private Date createOn;
    @Column(name = "updated_by")
    private Long updatedBy;
    @Column(name = "updated_on")
    private Date updatedOn;


}
