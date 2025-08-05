package com.rony.erpsoft.application_common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DropdownAppCodesDTO {
    private String xtype;
    private String xcode;
    private String description;
    private boolean active;
}
