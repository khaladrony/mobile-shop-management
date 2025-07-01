package com.rony.erpsoft.inventory.model;

import com.rony.erpsoft.application_common.model.BaseEntity;
import com.rony.erpsoft.inventory.enums.InventoryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "inventory_transfer")
public class InventoryTransfer extends BaseEntity {

    @Column(name = "transaction_id")
    private String transactionId;   //Id prefix => TO--;

    @Column(name = "reference")
    private String reference;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(name = "from_warehouse")
    private String fromWarehouse;

    @Column(name = "to_warehouse")
    private String toWarehouse;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InventoryStatus status;

    @Column(name = "remarks")
    private String remarks;

}
