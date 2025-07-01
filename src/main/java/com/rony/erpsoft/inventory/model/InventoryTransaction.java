package com.rony.erpsoft.inventory.model;

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

    @Column(name = "transaction_id")
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
    String supplierCode;    // SupplierInfo => supplierCode

    @Column(name = "customer_code")
    String customerCode;    // CustomerInfo => customerCode

    @Column(name = "document_type")
    private String documentType;    //RT,IS,TO,PO

    @Column(name = "document_id")
    private Long documentId;     //InventoryMovement,InventoryTransfer => id

    @Column(name = "document_transaction_id")
    private String documentTransactionId;     //InventoryMovementItem,InventoryTransferItem => transactionId

    @Column(name = "document_row")
    private int documentRow;    //InventoryMovementItem,InventoryTransferItem => row
}
