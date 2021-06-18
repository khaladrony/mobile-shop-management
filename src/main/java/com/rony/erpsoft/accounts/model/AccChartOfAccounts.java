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
@Table(name = "acc_chart_of_accounts")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AccChartOfAccounts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotNull(message = "Organization ID cannot be null")
    @Column(name="organization_id")
    private Long organizationId;

    @NotNull(message = "Accounts code cannot be null")
    @Size(min=1, max=20, message = "should be 1 to 20 chars")
    @Column(name="accounts_code")
    private String accountsCode;

    @NotNull(message = "Accounts name cannot be null")
    @Size(min=1, max=50, message = "should be 1 to 50 chars")
    @Column(name="accounts_name")
    private String accountsName;

    @NotNull(message = "Accounts type cannot be null")
    @Column(name="accounts_type")
    private String accountsType;    //Asset,Liability,Expenditure,Income
    @NotNull(message = "Accounts usage cannot be null")
    @Column(name="accounts_usage")
    private String accountsUsage;   //Cash,Bank,Ledger,AP,AR
    @NotNull(message = "Accounts source cannot be null")
    @Column(name="accounts_source")
    private String accountsSource;  //None,Subaccount,Customer,Supplier,Employee
    @Column(name="master_type")
    private String masterType;      //Balance Sheet,Revenue (Note: It's mainly use for report purpose)

    @Column(name="group1")
    private String group1;
    @Column(name="group2")
    private String group2;
    @Column(name="group3")
    private String group3;
    @Column(name="group4")
    private String group4;

    @Column(name="is_active")
    private boolean isActive;
    @Column(name="is_leaf")
    private boolean isLeaf;

    @Column(name="created_by")
    private Long createdBy;
    @Column(name="create_on")
    private Date createOn;
    @Column(name="updated_by")
    private Long updatedBy;
    @Column(name="updated_on")
    private Date updatedOn;

}
