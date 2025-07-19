package com.rony.erpsoft.inventory.inventorymovement.controller;

import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.inventory.enums.InventoryAction;
import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryMovementRequestDTO;
import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryMovementSearchDTO;
import com.rony.erpsoft.inventory.inventorymovement.service.InventoryMovementService;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.KEY;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.rony.erpsoft.utils.ApplicationConstants.FILTER;
import static com.rony.erpsoft.utils.ApplicationConstants.INVENTORY_MOVEMENT_BASE_URL;
import static com.rony.erpsoft.utils.ApplicationConstants.SORT_BY_ID;
import static com.rony.erpsoft.utils.ApplicationConstants.VIEW;
import static com.rony.erpsoft.utils.ApplicationConstants.VIEW_PAGE;

@RestController
@RequestMapping(INVENTORY_MOVEMENT_BASE_URL)
@AllArgsConstructor
public class InventoryMovementController extends AppProperty {

    private final AppUtil appUtil;
    private final InventoryMovementService inventoryMovementService;

    @GetMapping(value = VIEW)
    public ModelAndView view() {
        appUtil.genToken();
        ModelAndView modelAndView = new ModelAndView(VIEW_PAGE);
        modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());

        return modelAndView;
    }

    @PostMapping(value = FILTER)
    public AppResponse<Object> filter(
            @PageableDefault(size = 10, sort = SORT_BY_ID, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestBody InventoryMovementSearchDTO searchDTO
    ) {
        try {
            return AppResponse
                    .build(HttpStatus.OK)
                    .body(inventoryMovementService.findAll(searchDTO, pageable));
        } catch (Exception ex) {
            return AppResponse
                    .build(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(ex.getMessage());
        }
    }

    @GetMapping(value = "/search-transaction-id")
    public AppResponse<Object> searchTransactionIds(
            @RequestParam("action") InventoryAction action,
            @RequestParam("query") String query
    ) {
        List<String> result = inventoryMovementService.searchTransactionIds(action, query);
        return AppResponse.build(HttpStatus.OK).body(result);
    }

    @PostMapping(value = "/save")
    public AppResponse save(@RequestBody InventoryMovementRequestDTO requestDTO){
        return inventoryMovementService.createAndUpdate(requestDTO);
    }

    @PutMapping(value = "/update")
    public AppResponse update(@RequestBody InventoryMovementRequestDTO requestDTO) {
        return inventoryMovementService.createAndUpdate(requestDTO);
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<Object> get(@PathVariable("id") long id) {
        return AppResponse.build(HttpStatus.OK).body(inventoryMovementService.findById(id));
    }
}
