package com.rony.erpsoft.application_common.controller;


import com.rony.erpsoft.accounts.dto.SubCOADropdownDTO;
import com.rony.erpsoft.application_common.actionService.BankInfoActionService;
import com.rony.erpsoft.application_common.model.BankInfo;
import com.rony.erpsoft.application_common.service.BankInfoService;
import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.KEY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/application_common/bank_info")
public class BankInfoController extends AppProperty {

    @Autowired
    AppUtil appUtil;
    @Autowired
    BankInfoActionService bankInfoActionService;
    @Autowired
    BankInfoService bankInfoService;

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView view() {
        appUtil.genToken();
        ModelAndView modelAndView = new ModelAndView("application_common/view");
        modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());

        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AppResponse<BankInfo> save(@RequestBody BankInfo bankInfo){
        return bankInfoActionService.execute(bankInfo);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public AppResponse<BankInfo> update(@RequestBody BankInfo bankInfo) {
        return bankInfoActionService.execute(bankInfo);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<Object> get(@PathVariable("id") long id) {
        BankInfo model = bankInfoService.findById(id);
        if (model != null) {
            return AppResponse.build(HttpStatus.OK).body(model);
        } else {
            return AppResponse.build(HttpStatus.NOT_FOUND).message("Voucher not found");
        }
    }

    @RequestMapping(value = "/filter/{currentPage}/{itemPerPage}", method = RequestMethod.POST)
    public AppResponse<Object> filter(
            @PathVariable("currentPage") int currentPage,
            @PathVariable("itemPerPage") int itemPerPage,
            @RequestBody Map<String, Object> params) {
        try {
            return AppResponse.build(HttpStatus.OK).body(bankInfoService.filter(currentPage, itemPerPage, params));
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @RequestMapping(value = "/get/list", method = RequestMethod.GET)
    public AppResponse<Object> getBankAccountList() {
        try {
            List<SubCOADropdownDTO> list = bankInfoService.getBankAccountListForDropDown();
            if (!list.isEmpty()) {
                return AppResponse.build(HttpStatus.OK).body(list);
            } else {
                return AppResponse.build(HttpStatus.NO_CONTENT).message("Bank A/C not found");
            }
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }
}
