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
@Table(name = "employee_info")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EmployeeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Organization ID cannot be null")
    @Column(name = "organization_id")
    private Long organizationId;

    @Column(name = "employee_code", unique = true)
    private String employeeCode;
    @Column(name = "employee_name")
    private String employeeName;
    @Column(name = "employee_pin", unique = true)
    private String employeePin;
    @Column(name = "designation")
    private String designation;
    @Column(name = "department")
    private String department;
    @Column(name = "salary")
    private double salary;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name="chart_of_accounts_id")
    private Long chartOfAccountsId;
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
