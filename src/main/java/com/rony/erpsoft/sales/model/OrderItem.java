package com.rony.erpsoft.sales.model;

import com.rony.erpsoft.application_common.model.BaseEntity;
import com.rony.erpsoft.inventory.itemmaster.model.ItemMaster;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem extends BaseEntity {

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "line_number")
    private int lineNumber;

    @Column(name="item_master_id", nullable = false)
    private Long itemMasterId;

    @Column(name="item_code", nullable = false)
    private String itemCode;

    @Column(name="item_name", nullable = false)
    private String itemName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal price = BigDecimal.ZERO;

    @Column(name = "cost_price", nullable = false)
    private BigDecimal costPrice = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;
}