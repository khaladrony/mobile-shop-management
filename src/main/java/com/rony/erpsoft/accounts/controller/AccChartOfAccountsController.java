package com.rony.erpsoft.accounts.controller;

import com.rony.erpsoft.accounts.actionService.CommonActionService;
import com.rony.erpsoft.accounts.actionService.chartOfAccounts.GetAllChartOfAccountsActionService;
import com.rony.erpsoft.accounts.actionService.chartOfAccounts.SaveChartOfAccountsActionService;
import com.rony.erpsoft.accounts.model.AccChartOfAccounts;
import com.rony.erpsoft.accounts.model.enums.VoucherStatus;
import com.rony.erpsoft.accounts.service.AccChartOfAccountsService;
import com.rony.erpsoft.accounts.service.AccountsReportService;
import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.KEY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts/chart_of_accounts")
public class AccChartOfAccountsController extends AppProperty {

    @Autowired
    AppUtil appUtil;
    @Autowired
    AccChartOfAccountsService accChartOfAccountsService;
    @Autowired
    SaveChartOfAccountsActionService saveChartOfAccountsActionService;
    @Autowired
    GetAllChartOfAccountsActionService getAllChartOfAccountsActionService;
    @Autowired
    CommonActionService commonActionService;
    @Autowired
    AccountsReportService accountsReportService;


    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView view() {
        appUtil.genToken();
        ModelAndView modelAndView = new ModelAndView("accounts/view");
        modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());

        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AppResponse<AccChartOfAccounts> save(@RequestBody AccChartOfAccounts chartOfAccounts) {
        return saveChartOfAccountsActionService.execute(chartOfAccounts);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public AppResponse<AccChartOfAccounts> update(@RequestBody AccChartOfAccounts chartOfAccounts) {
        return saveChartOfAccountsActionService.execute(chartOfAccounts);
    }

    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public AppResponse<Object> getAll() {
        return getAllChartOfAccountsActionService.execute();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<Object> get(@PathVariable("id") long id) {
        AccChartOfAccounts model = accChartOfAccountsService.findById(id);
        if (model != null) {
            return AppResponse.build(HttpStatus.OK).body(model);
        } else {
            return AppResponse.build(HttpStatus.NOT_FOUND).message("Chart of accounts not found");
        }
    }

    @RequestMapping(value = "/get/coa_list", method = RequestMethod.GET)
    public AppResponse<Object> getChartOfAccountList() {
        try {
            List<Map<String, Object>> list = accChartOfAccountsService.findAllCOAListForDropDown();
            if (!list.isEmpty()) {
                return AppResponse.build(HttpStatus.OK).body(list);
            } else {
                return AppResponse.build(HttpStatus.NO_CONTENT).message("Chart of accounts not found");
            }
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @RequestMapping(value = "/get/coa_by_usage/{paymentType}", method = RequestMethod.GET)
    public AppResponse<Object> getChartOfAccountsByAccountsUsage(@PathVariable("paymentType") String chartOfAccountsUsage) {
        try {
            Map<String, Object> chartOfAccountsUsageObj = accChartOfAccountsService.findChartOfAccountsByAccountsUsage(chartOfAccountsUsage);
            if (!chartOfAccountsUsageObj.isEmpty()) {
                return AppResponse.build(HttpStatus.OK).body(chartOfAccountsUsageObj);
            } else {
                return AppResponse.build(HttpStatus.NO_CONTENT).message("Chart of accounts not found");
            }
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @RequestMapping(value = "/get/voucher_status_list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<Object> getVoucherStatusList() {
        List<VoucherStatus> voucherStatusList = commonActionService.getVoucherStatusList();
        if (!voucherStatusList.isEmpty()) {
            return AppResponse.build(HttpStatus.OK).body(voucherStatusList);
        } else {
            return AppResponse.build(HttpStatus.NOT_FOUND).message("Voucher status list not found");
        }
    }

    @RequestMapping(value = "/filter/{currentPage}/{itemPerPage}", method = RequestMethod.POST)
    public AppResponse<Object> filter(
            @PathVariable("currentPage") int currentPage,
            @PathVariable("itemPerPage") int itemPerPage,
            @RequestBody Map<String, Object> params) {
        try {
            return AppResponse.build(HttpStatus.OK).body(accChartOfAccountsService.filter(currentPage, itemPerPage, params));
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @RequestMapping(value = "/count_voucher/{chartOfAccountsId}", method = RequestMethod.POST)
    public AppResponse<Object> getVoucherCountByChartOfAccountsId(
            @PathVariable("chartOfAccountsId") int chartOfAccountsId) {
        try {
            return AppResponse.build(HttpStatus.OK).body(accChartOfAccountsService.getVoucherCountByChartOfAccountsId(chartOfAccountsId));
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

}
