package com.rony.erpsoft.inventory.itemmaster.dto;

import com.rony.erpsoft.inventory.itemmaster.model.TrackingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemDTO {

    private Long id;
    private String name;
    private String specification;
    private BigDecimal price;
    private BigDecimal costPrice;
    private String fileName;
    private String category;
    private String brand;
    private TrackingType trackingType;
}