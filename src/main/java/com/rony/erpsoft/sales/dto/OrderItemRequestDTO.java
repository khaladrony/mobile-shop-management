package com.rony.erpsoft.sales.dto;

import com.rony.erpsoft.inventory.itemmaster.model.ItemMaster;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {
    private int lineNumber;
    private Long itemMasterId;
    private String itemCode;
    private String itemName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal total;
}
