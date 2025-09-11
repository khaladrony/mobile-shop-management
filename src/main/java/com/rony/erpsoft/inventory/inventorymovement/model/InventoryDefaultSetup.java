package com.rony.erpsoft.inventory.inventorymovement.model;

import com.rony.erpsoft.application_common.model.BaseEntity;
import com.rony.erpsoft.inventory.enums.ValuationMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inventory_default_setup")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDefaultSetup extends BaseEntity {

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "warehouse_enabled")
    private boolean warehouseEnabled;

    // Default warehouse for POS and issues
    @Column(name = "default_warehouse")
    private String defaultWarehouse;

    // Valuation method: FIFO, LIFO, WEIGHTED_AVG
    @Enumerated(EnumType.STRING)
    @Column(name = "valuation_method")
    private ValuationMethod valuationMethod;

    // Allow negative stock?
    @Column(name = "allow_negative_stock")
    private boolean allowNegativeStock = false;

    // Default accounts (integration with Accounting)
    @Column(name = "stock_account")
    private String stockAccount;
}
