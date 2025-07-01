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
@Table(name = "item_master")
public class ItemMaster extends BaseEntity {

    @Column(name="item_code")
    private String itemCode;

    @Column(name="item_name")
    private String itemName;

    @Column(name="description")
    private String description;

    @Column(name="category")
    private String category;

    @Column(name="brand")
    private String brand;

    @Column(name="model")
    private String model;

    @Column(name="color")
    private String color;

    @Column(name="storage")
    private String storage; // e.g., "64GB", "128GB"

    @Column(name="unit")
    private String unit;

    @Column(name="price")
    private BigDecimal price;

    @Column(name="standard_price")
    private BigDecimal standardPrice;

    @Column(name="standard_cost")
    private BigDecimal standardCost;

    @Column(name="imei")
    private String imei;

    @Column(name="active")
    private boolean active;
}
