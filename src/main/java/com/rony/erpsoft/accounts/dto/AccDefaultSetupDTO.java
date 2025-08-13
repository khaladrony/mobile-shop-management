package com.rony.erpsoft.accounts.dto;

import lombok.Data;

@Data
public class AccDefaultSetupDTO {

    private Long id;
    private Long organizationId;
    private String voucherPrintView;        //A4, A5
    private boolean masterParticularRequired;
    private boolean detailParticularRequired;
}
