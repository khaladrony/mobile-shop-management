package com.rony.erpsoft.inventory.inventorymovement.dto;

import com.rony.erpsoft.inventory.enums.IMEIStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImeiDTO {
    private Long id;
    private Long organizationId;
    private String imeiValue;
    private String itemCode;
    private IMEIStatus status; // AVAILABLE, SOLD, RETURNED
    private Long inventoryMovementItemId;
}
