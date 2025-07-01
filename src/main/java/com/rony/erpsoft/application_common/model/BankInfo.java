package com.rony.erpsoft.application_common.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "bank_info")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BankInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Organization ID cannot be null")
    @Column(name = "organization_id")
    private Long organizationId;

    @Column(name = "bank_account_code", unique = true)
    String bankAccountCode;
    @Column(name = "bank_account_no")
    String bankAccountNo;
    @Column(name = "bank_account_name")
    String bankAccountName;
    @Column(name = "bank_name")
    String bankName;
    @Column(name = "branch_name")
    String branchName;
    @Column(name = "phone")
    String phone;
    @Column(name = "address")
    String address;
    @Column(name="status")
    private boolean status;

    @Column(name="created_by")
    private Long createdBy;
    @Column(name="create_on")
    private Date createOn;
    @Column(name="updated_by")
    private Long updatedBy;
    @Column(name="updated_on")
    private Date updatedOn;
}

