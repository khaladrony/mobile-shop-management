package com.rony.erpsoft.inventory.inventorymovement.dto;

import com.rony.erpsoft.inventory.enums.ValuationMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDefaultSetupDTO {
    private Long id;
    private Long organizationId;
    private String branchCode;
    private boolean warehouseEnabled;
    private String defaultWarehouse;
    private ValuationMethod valuationMethod;
    private boolean allowNegativeStock = false;
    private String stockAccount;
}
