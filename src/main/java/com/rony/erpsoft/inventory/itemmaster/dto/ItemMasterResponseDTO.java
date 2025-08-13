package com.rony.erpsoft.inventory.itemmaster.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemMasterResponseDTO {

    private Long id;
    private Long organizationId;
    private String itemCode;
    private String itemName;
    private String description;
    private String category;
    private String brand;
    private String model;
    private String color;
    private String storage; // e.g., "64GB", "128GB"
    private String unit;
    private BigDecimal price;
    private BigDecimal standardPrice;
    private BigDecimal standardCost;
    private String imei;
    private boolean active;
    private Long createdBy;
    private String fileName;
}
