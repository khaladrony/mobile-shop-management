package com.rony.erpsoft.sales.dto;

import com.rony.erpsoft.sales.model.OrderItem;
import com.rony.erpsoft.sales.model.enums.DiscountType;
import com.rony.erpsoft.sales.model.enums.OrderStatus;
import com.rony.erpsoft.sales.model.enums.PaymentType;
import com.rony.erpsoft.sales.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private Long id;
    private String transactionId;
    private LocalDateTime transactionDate;
    private TransactionType transactionType;
    private BigDecimal grossAmount;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private BigDecimal netAmount;
    private BigDecimal vatAmount;
    private BigDecimal totalAmount;
    private BigDecimal givenAmount;
    private BigDecimal returnAmount;
    private PaymentType paymentType;
    private OrderStatus status;
    private String customerName;
    private String mobileNumber;
    private String referenceNo;
    private List<OrderItemRequestDTO> items;
}
