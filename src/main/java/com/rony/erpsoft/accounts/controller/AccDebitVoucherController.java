package com.rony.erpsoft.accounts.controller;

import com.rony.erpsoft.accounts.actionService.voucher.VoucherActionService;
import com.rony.erpsoft.accounts.model.AccJournalMaster;
import com.rony.erpsoft.accounts.service.AccJournalMasterService;
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
@RequestMapping("/accounts/debit_voucher")
public class AccDebitVoucherController extends AppProperty {

    @Autowired
    AppUtil appUtil;
    @Autowired
    VoucherActionService voucherActionService;
    @Autowired
    AccJournalMasterService accJournalMasterService;


    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView view(){
        appUtil.genToken();
        ModelAndView modelAndView = new ModelAndView("accounts/view");
        modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());

        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AppResponse<AccJournalMaster> save(@RequestBody AccJournalMaster accJournalMaster){
        return voucherActionService.execute(accJournalMaster);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public AppResponse<AccJournalMaster> update(@RequestBody AccJournalMaster journalMaster) {
        return voucherActionService.execute(journalMaster);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<Object> get(@PathVariable("id") long id) {
        AccJournalMaster model = accJournalMasterService.findById(id);
        if (model != null) {
            return AppResponse.build(HttpStatus.OK).body(model);
        } else {
            return AppResponse.build(HttpStatus.NOT_FOUND).message("Voucher not found");
        }
    }

    @RequestMapping(value = "/get/voucher_list/{voucherType}", method = RequestMethod.GET)
    public AppResponse<Object> getVoucherListByVoucherType(@PathVariable("voucherType") String voucherType) {
        try{
            List<Map<String, Object>> debitVoucherList = accJournalMasterService.getVoucherListByVoucherType(voucherType);
            if (!debitVoucherList.isEmpty()) {
                return AppResponse.build(HttpStatus.OK).body(debitVoucherList);
            } else {
                return AppResponse.build(HttpStatus.NO_CONTENT).message("Debit Voucher list not found");
            }
        } catch (Exception ex){
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @RequestMapping(value = "/filter/{currentPage}/{itemPerPage}", method = RequestMethod.POST)
    public AppResponse<Object> filter(
            @PathVariable("currentPage") int currentPage,
            @PathVariable("itemPerPage") int itemPerPage,
            @RequestBody Map<String, Object> params) {
        try {
            return AppResponse.build(HttpStatus.OK).body(accJournalMasterService.filter(currentPage, itemPerPage, params));
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

}
