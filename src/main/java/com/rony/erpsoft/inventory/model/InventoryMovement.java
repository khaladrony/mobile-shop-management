package com.rony.erpsoft.inventory.model;

import com.rony.erpsoft.application_common.model.BaseEntity;
import com.rony.erpsoft.inventory.enums.InventoryAction;
import com.rony.erpsoft.inventory.enums.InventoryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "inventory_movement")
public class InventoryMovement extends BaseEntity {

    @Column(name = "transaction_id")
    private String transactionId;   //Id prefix => RE--:IS-- (Receipt:Issue)

    @Column(name = "reference")
    private String reference;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(name = "warehouse")
    private String warehouse;

    @Column(name = "sign")
    private int sign;   //Receipt => 1, Issue => -1

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private InventoryAction action;

    @Column(name = "year")
    private int year;

    @Column(name = "month")
    private int month;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InventoryStatus status;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "supplier_code")
    String supplierCode;    // SupplierInfo => supplierCode

    @Column(name = "customer_code")
    String customerCode;    // CustomerInfo => customerCode
}
