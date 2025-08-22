package com.rony.erpsoft.application_common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerSearchDTO {

    private Long id;
    private String customerCode;
    private String customerName;
    private String mobileNumber;
}
