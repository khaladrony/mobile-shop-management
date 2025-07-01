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
@Table(name = "inventory_transfer_item")
public class InventoryTransferItem extends BaseEntity {

    @Column(name = "inventory_transfer_id")
    private Long InventoryTransferId;   //InventoryTransfer =>id

    @Column(name = "transaction_id")
    private String transactionId;   //InventoryTransfer =>transactionId

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
