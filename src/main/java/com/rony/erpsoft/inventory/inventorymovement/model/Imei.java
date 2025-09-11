package com.rony.erpsoft.inventory.inventorymovement.model;

import com.rony.erpsoft.application_common.model.BaseEntity;
import com.rony.erpsoft.inventory.enums.IMEIStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "imei", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"imei_value"})
})
public class Imei extends BaseEntity {

    @Column(name = "imei_value", nullable = false, unique = true, columnDefinition = "varchar(255) COLLATE utf8mb4_bin")
    private String imeiValue;      // unique

    @Column(name = "item_code", nullable = false)
    private String itemCode;            // belongs to which model

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private IMEIStatus status; // AVAILABLE, SOLD, RETURNED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_movement_item_id", insertable = false, updatable = false)
    private InventoryMovementItem inventoryMovementItem;
}
