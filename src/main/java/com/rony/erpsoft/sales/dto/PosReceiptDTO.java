package com.rony.erpsoft.sales.dto;

import com.rony.erpsoft.user_auth.model.Branch;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class PosReceiptDTO {

    private LocalDateTime date;
    private String cashier;      // you can map from authenticated user or DB
    private String txnNo;        // transactionId
    private List<PosReceiptItemDTO> items;

    private BigDecimal discount;     // discountValue
    private BigDecimal vatRate;      // % (derive if needed)
    private BigDecimal serviceCharge;

    private String paymentMethod;    // paymentType
    private BigDecimal paid;         // givenAmount

    private BigDecimal grossAmount;
    private BigDecimal netAmount;
    private BigDecimal vatAmount;
    private BigDecimal totalAmount;
    private BigDecimal returnAmount;

    private String customerName;
    private String mobileNumber;

    private Branch branch;
    private String footerNote;
}
