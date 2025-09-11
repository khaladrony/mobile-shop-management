package com.rony.erpsoft.user_auth.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDTO {

    private long id;
    private String name;
    private String title;
    private String shortName;
    private String address1;
    private String address2;
    private String webUrl;
    private String email;
    private String contactName;
    private String contactPhone;
    private String logo;
    private boolean active;
}
