package com.rony.erpsoft.accounts.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "acc_sub_accounts")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AccSubAccounts implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotNull(message = "Organization ID cannot be null")
    @Column(name="organization_id")
    private Long organizationId;

    @NotNull(message = "Char of accounts ID cannot be null")
    @Column(name="chart_of_accounts_id")
    private Long chartOfAccountsId;

    @Transient
    private String chartOfAccountsCode;

    @NotNull(message = "Sub-accounts code cannot be null")
    @Size(min=1, max=20, message = "should be 1 to 20 chars")
    @Column(name="sub_accounts_code")
    private String subAccountsCode;

    @NotNull(message = "Sub-accounts name cannot be null")
    @Size(min=1, max=50, message = "should be 1 to 50 chars")
    @Column(name="sub_accounts_name")
    private String subAccountsName;

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
