package com.rony.erpsoft.sales.model;

import com.rony.erpsoft.application_common.model.BaseEntity;
import com.rony.erpsoft.sales.model.enums.DiscountType;
import com.rony.erpsoft.sales.model.enums.OrderStatus;
import com.rony.erpsoft.sales.model.enums.PaymentType;
import com.rony.erpsoft.sales.model.enums.TransactionType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"transaction_id"})
})
public class Order extends BaseEntity {

    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType = TransactionType.POS;

    @Column(name = "gross_amount", nullable = false)
    private BigDecimal grossAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type")
    private DiscountType discountType = DiscountType.PERCENT;

    @Column(name = "discount_value")
    private BigDecimal discountValue = BigDecimal.ZERO;

    @Column(name = "net_amount", nullable = false)
    private BigDecimal netAmount = BigDecimal.ZERO;

    @Column(name = "vat_amount", nullable = false)
    private BigDecimal vatAmount = BigDecimal.ZERO;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "given_amount", nullable = false)
    private BigDecimal givenAmount = BigDecimal.ZERO;

    @Column(name = "return_amount")
    private BigDecimal returnAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType = PaymentType.CASH;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.COMPLETED;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "reference_no")
    private String referenceNo;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "order_id")
    private List<OrderItem> items = new ArrayList<>();
}