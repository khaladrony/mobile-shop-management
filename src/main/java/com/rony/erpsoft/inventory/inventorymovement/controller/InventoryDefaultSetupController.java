package com.rony.erpsoft.inventory.inventorymovement.controller;

import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryDefaultSetupDTO;
import com.rony.erpsoft.inventory.inventorymovement.service.InventoryDefaultSetupService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.rony.erpsoft.utils.ApplicationConstants.INVENTORY_DEFAULT_SETUP_BASE_URL;

@RestController
@RequestMapping(INVENTORY_DEFAULT_SETUP_BASE_URL)
@AllArgsConstructor
public class InventoryDefaultSetupController {

    private final InventoryDefaultSetupService defaultSetupService;

    @GetMapping(value = "/branchCode")
    public AppResponse<InventoryDefaultSetupDTO> getByBranchCode() {

        InventoryDefaultSetupDTO models = defaultSetupService.findByBranchCode();
        if (models != null) {
            return AppResponse.build(HttpStatus.OK).body(models);
        } else {
            return AppResponse.build(HttpStatus.NO_CONTENT).message("No default setup found for inventory");
        }
    }
}
