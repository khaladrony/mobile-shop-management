package com.rony.erpsoft.inventory.model;

import com.rony.erpsoft.application_common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "inventory_movement_item")
public class InventoryMovementItem extends BaseEntity {

    @Column(name = "inventory_movement_id")
    private Long InventoryMovementId;   //InventoryMovement =>id

    @Column(name = "transaction_id")
    private String transactionId;   //InventoryMovement =>transactionId

    @Column(name = "line_number")
    private int lineNumber;

    @Column(name = "item_code")
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
    private Long InventoryTransactionId;
}
