package com.rony.erpsoft.user_auth.model;

import com.rony.erpsoft.application_common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "branch")
@Getter
@Setter
@NoArgsConstructor
public class Branch extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String code;

    private String name;
    private String address;
    private String phone;
    private String managerName;

    private boolean active = true;
}
