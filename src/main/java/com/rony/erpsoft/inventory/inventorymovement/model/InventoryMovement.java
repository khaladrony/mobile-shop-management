package com.rony.erpsoft.inventory.inventorymovement.model;

import com.rony.erpsoft.application_common.model.BaseEntity;
import com.rony.erpsoft.inventory.enums.InventoryAction;
import com.rony.erpsoft.inventory.enums.InventoryStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "inventory_movement", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"transaction_id"})
})
public class InventoryMovement extends BaseEntity {

    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;   //Id prefix => RE--:IS--:TO-- (Receipt:Issue:Transfer)

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(name = "reference")
    private String reference;

    @Column(name = "warehouse")
    private String warehouse;

    @Column(name = "from_warehouse")
    private String fromWarehouse;   //use for transfer

    @Column(name = "to_warehouse")
    private String toWarehouse;     //use for transfer

    @Column(name = "sign")
    private int sign;   //Receipt => 1, Issue => -1, Transfer => 2

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    private InventoryAction action;

    @Column(name = "year")
    private int year;

    @Column(name = "month")
    private int month;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InventoryStatus status;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "supplier_code")
    String supplierCode;    // SupplierInfo => supplierCode

    @Column(name = "customer_code")
    String customerCode;    // CustomerInfo => customerCode

    @Column(name = "branch_code")
    private String branchCode;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "inventory_movement_id")
    private List<InventoryMovementItem> details = new ArrayList<>();
}
