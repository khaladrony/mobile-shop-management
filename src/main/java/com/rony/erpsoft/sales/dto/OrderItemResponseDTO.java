package com.rony.erpsoft.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {
    private Long id;
    private Long organizationId;
    private Long orderId;
    private int lineNumber;
    private Long itemMasterId;
    private String itemCode;
    private String itemName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal total;
}
