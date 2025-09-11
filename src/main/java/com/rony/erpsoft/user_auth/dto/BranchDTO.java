package com.rony.erpsoft.user_auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BranchDTO {
    private Long id;
    private String code;
    private String name;
    private String address;
    private String phone;
    private String managerName;
    private boolean active;
}
