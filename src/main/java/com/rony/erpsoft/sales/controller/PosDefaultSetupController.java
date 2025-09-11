package com.rony.erpsoft.sales.controller;

import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.sales.dto.OrderDefaultSetupDTO;
import com.rony.erpsoft.sales.service.OrderDefaultSetupService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.rony.erpsoft.utils.ApplicationConstants.POS_DEFAULT_SETUP_BASE_URL;

@RestController
@RequestMapping(POS_DEFAULT_SETUP_BASE_URL)
@AllArgsConstructor
public class PosDefaultSetupController {

    private final OrderDefaultSetupService defaultSetupService;

    @GetMapping(value = "/branchCode")
    public AppResponse<OrderDefaultSetupDTO> getByBranchCode() {

        OrderDefaultSetupDTO models = defaultSetupService.findByBranchCode();
        if (models != null) {
            return AppResponse.build(HttpStatus.OK).body(models);
        } else {
            return AppResponse.build(HttpStatus.NO_CONTENT).message("No default setup found");
        }
    }

}
