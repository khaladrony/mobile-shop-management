package com.rony.erpsoft.sales.dto;

import com.rony.erpsoft.sales.model.enums.OrderStatus;
import com.rony.erpsoft.sales.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDetailsDTO {
    private Long id;
    private Long organizationId;
    private String transactionId;
    private LocalDateTime transactionDate;
    private OrderStatus status;
    private Long orderId;
    private String itemCode;
    private String itemName;
    private BigDecimal costPrice;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal grossAmount;
    private BigDecimal discountAmount;
    private BigDecimal netAmount;
    private BigDecimal vatAmount;
    private BigDecimal totalAmount;
}
