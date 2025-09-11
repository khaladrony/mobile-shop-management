package com.rony.erpsoft.inventory.inventorymovement.model;

import com.rony.erpsoft.application_common.model.BaseEntity;
import com.rony.erpsoft.inventory.enums.InventoryAction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "inventory_transaction")
public class InventoryTransaction extends BaseEntity {

    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;   // Id prefix => IMRE:IMIS (Receipt:Issue)

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

    @Column(name = "item_code")
    private String itemCode;    // ItemMaster => itemCode

    @Column(name = "line_number")
    private int lineNumber;

    @Column(name = "unit")
    private String unit;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "supplier_code")
    private String supplierCode;    // SupplierInfo => supplierCode

    @Column(name = "customer_code")
    private String customerCode;    // CustomerInfo => customerCode

    @Column(name = "document_no")
    private String documentNo;    //RT--000001,IS--000001,.....

    @Column(name = "document_id")
    private Long documentId;     //InventoryMovement => id

    @Column(name = "document_detail_id")
    private Long documentDetailId;     //InventoryMovementItem => id

    @Column(name = "document_row")
    private int documentRow;    //InventoryMovementItem => lineNumber

    @Column(name = "branch_code")
    private String branchCode;
}
