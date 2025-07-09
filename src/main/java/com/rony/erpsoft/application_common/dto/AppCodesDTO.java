package com.rony.erpsoft.application_common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppCodesDTO {
    private Long id;
    private Long organizationId;
    private String xtype;
    private String xcode;
    private String description;
    private boolean active;
    private Long createdBy;
    private LocalDateTime createOn;
    private Long updatedBy;
    private LocalDateTime updatedOn;
}
