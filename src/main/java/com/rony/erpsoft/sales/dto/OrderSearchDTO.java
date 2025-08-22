package com.rony.erpsoft.sales.dto;

import com.rony.erpsoft.sales.model.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderSearchDTO {
    private String transactionId;
    private LocalDateTime transactionDate;
    private OrderStatus status;
    private String customerName;
    private String mobileNumber;
    // Add these for sorting
    private String sortField;
    private String sortDirection; // "asc" or "desc"
}
