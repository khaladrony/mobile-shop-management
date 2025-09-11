package com.rony.erpsoft.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PosReceiptItemDTO {
    private String name;        // itemName
    private Integer qty;        // quantity
    private BigDecimal price;   // price
    private BigDecimal total;   // price
    private BigDecimal discount;
    private String note;        // remarks or promo
}
