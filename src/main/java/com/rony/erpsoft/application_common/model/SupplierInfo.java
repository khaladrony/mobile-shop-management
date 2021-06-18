package com.rony.erpsoft.application_common.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Table(name = "supplier_info")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SupplierInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Organization ID cannot be null")
    @Column(name = "organization_id")
    private Long organizationId;

    @Column(name = "supplier_code", unique = true)
    String supplierCode;
    @Column(name = "supplier_name")
    String supplierName;
    @Column(name = "email")
    String email;
    @Column(name = "phone")
    String phone;
    @Column(name = "address")
    String address;
    @Column(name="chart_of_accounts_id")
    private boolean chartOfAccountsId;
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
