package com.rony.erpsoft.application_common.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_codes")
public class AppCodes extends BaseEntity{

    @Column(name = "xtype")
    private String xtype;

    @Column(name = "xcode")
    private String xcode;

    @Column(name = "description")
    private String description;

    @Column(name="active")
    private boolean active;
}
