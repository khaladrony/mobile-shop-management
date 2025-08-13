package com.rony.erpsoft.accounts.controller;

import com.rony.erpsoft.accounts.dto.AccDefaultSetupDTO;
import com.rony.erpsoft.accounts.service.AccDefaultSetupService;
import com.rony.erpsoft.configuration.AppResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts/default-setup")
@AllArgsConstructor
public class AccDefaultSetupController {

    private final AccDefaultSetupService service;

    @GetMapping("/organization")
    public AppResponse<AccDefaultSetupDTO> getByOrganizationId() {
        AccDefaultSetupDTO dto = service.findByOrganizationId();
        if (dto != null) {
            return AppResponse.build(HttpStatus.OK).body(dto);
        } else {
            return AppResponse.build(HttpStatus.NOT_FOUND).message("Accounts default setup not found");
        }
    }
}
