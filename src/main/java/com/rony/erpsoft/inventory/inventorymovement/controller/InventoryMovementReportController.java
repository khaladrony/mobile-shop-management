package com.rony.erpsoft.inventory.inventorymovement.controller;

import com.rony.erpsoft.accounts.actionService.CommonActionService;
import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.inventory.enums.InventoryAction;
import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryTransactionSearchDTO;
import com.rony.erpsoft.inventory.inventorymovement.service.InventoryReportService;
import com.rony.erpsoft.inventory.inventorymovement.service.InventoryTransactionService;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.KEY;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

import static com.rony.erpsoft.utils.ApplicationConstants.INVENTORY_REPORT_BASE_URL;
import static com.rony.erpsoft.utils.ApplicationConstants.VIEW;
import static com.rony.erpsoft.utils.ApplicationConstants.VIEW_PAGE;

@RestController
@RequestMapping(INVENTORY_REPORT_BASE_URL)
@AllArgsConstructor
public class InventoryMovementReportController extends AppProperty {

    private final AppUtil appUtil;
    private final InventoryReportService inventoryReportService;
    private final CommonActionService commonActionService;

    @GetMapping(value = VIEW)
    public ModelAndView view() {
        appUtil.genToken();
        ModelAndView modelAndView = new ModelAndView(VIEW_PAGE);
        modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());

        return modelAndView;
    }

    @GetMapping(value = "/item-ledger")
    public ResponseEntity<Resource> itemLedgerPreview(
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate,
            @RequestParam(value = "warehouse") String warehouse,
            @RequestParam(value = "itemCode", required = false) String itemCode,
            @RequestParam(value = "reportType", required = false) String reportType
    ) {

        InventoryTransactionSearchDTO searchDTO = new InventoryTransactionSearchDTO();
        searchDTO.setFromDate(fromDate);
        searchDTO.setToDate(toDate);
        searchDTO.setWarehouse(warehouse);
        searchDTO.setItemCode(itemCode);
        searchDTO.setReportType(reportType);

        ByteArrayResource resource = inventoryReportService.itemLedger(searchDTO);

        String fileName = AppUtil.getStringBetweenTwoCharacters(resource.getDescription());

        return commonActionService.reportHeader(fileName, resource);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Resource> rcvIssuePreview(@PathVariable("id") long id) {

        ByteArrayResource resource = inventoryReportService.rcvIssuePreview(id);

        String filename = AppUtil.getStringBetweenTwoCharacters(resource.getDescription());

        return commonActionService.reportHeader(filename, resource);
    }
}
