package com.rony.erpsoft.accounts.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubCOADropdownDTO {
    private Long id;
    private String name;
    private String code;
    private String subLabel;
    private Long chartOfAccountsId;
}
