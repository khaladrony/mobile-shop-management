package com.rony.erpsoft.inventory.inventorymovement.model;

import com.rony.erpsoft.application_common.model.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "inventory_movement_item")
public class InventoryMovementItem extends BaseEntity {

    @Column(name = "inventory_movement_id", nullable = false)
    private Long inventoryMovementId;   //InventoryMovement =>id

    @Column(name = "line_number")
    private int lineNumber;

    @Column(name = "item_code", nullable = false)
    private String itemCode;    // ItemMaster => itemCode

    @Column(name = "unit")
    private String unit;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "quantity_confirm")
    private BigDecimal quantityConfirm;

    @Column(name = "inventory_transaction_id")
    private Long inventoryTransactionId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "inventory_movement_item_id") // parent owns FK
    private List<Imei> imeiNumbers = new ArrayList<>();

    @Column(name = "document_id")
    private Long documentId;
}
