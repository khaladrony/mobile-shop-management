package com.rony.erpsoft.accounts.controller;

import com.rony.erpsoft.accounts.actionService.accSubAccounts.SaveSubAccountsActionService;
import com.rony.erpsoft.accounts.model.AccSubAccounts;
import com.rony.erpsoft.accounts.service.AccSubAccountsService;
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
@RequestMapping("/accounts/sub_accounts")
public class AccSubAccountsController extends AppProperty {
    @Autowired
    AppUtil appUtil;
    @Autowired
    AccSubAccountsService accSubAccountsService;
    @Autowired
    SaveSubAccountsActionService saveSubAccountsActionService;


    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView view() {
        appUtil.genToken();
        ModelAndView modelAndView = new ModelAndView("accounts/view");
        modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());

        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AppResponse<AccSubAccounts> save(@RequestBody AccSubAccounts subAccounts) {
        return saveSubAccountsActionService.execute(subAccounts);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public AppResponse<AccSubAccounts> update(@RequestBody AccSubAccounts subAccounts) {
        return saveSubAccountsActionService.execute(subAccounts);
    }

    @RequestMapping(value = "/get_all/coa/{chartOfAccountsId}", method = RequestMethod.GET)
    public AppResponse<Object> getAll(@PathVariable("chartOfAccountsId") long chartOfAccountsId) {
        try{
            List<AccSubAccounts> list = accSubAccountsService.findAllByCoa(chartOfAccountsId);
            if (!list.isEmpty()) {
                return AppResponse.build(HttpStatus.OK).body(list);
            } else {
                return AppResponse.build(HttpStatus.NO_CONTENT).message("No sub account found");
            }
        } catch (Exception ex){
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @RequestMapping(value = "/get/sub_account_code/{chartOfAccountsId}/{chartOfAccountsCode}", method = RequestMethod.GET)
    public AppResponse<Object> get(@PathVariable("chartOfAccountsId") long chartOfAccountsId,
                                   @PathVariable("chartOfAccountsCode") String chartOfAccountsCode) {
        try {
            String subAccountsCode = accSubAccountsService.subAccountsCodeGenerate(chartOfAccountsId, chartOfAccountsCode);
            if (!subAccountsCode.isEmpty()) {
                return AppResponse.build(HttpStatus.OK).body(subAccountsCode);
            } else {
                return AppResponse.build(HttpStatus.NO_CONTENT).message("Sub accounts code not found");
            }
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @RequestMapping(value = "/get/sub_account_list/{accountsSource}/{chartOfAccountsId}", method = RequestMethod.GET)
    public AppResponse<Object> getSubAccountListByAccountsSource(@PathVariable("accountsSource") String accountsSource,
                                                                 @PathVariable("chartOfAccountsId") long chartOfAccountsId) {
        try {
            List<Map<String, Object>> subAccountList = accSubAccountsService.findAllSubAccountListByAccountsSource(accountsSource, chartOfAccountsId);
            if (!subAccountList.isEmpty()) {
                return AppResponse.build(HttpStatus.OK).body(subAccountList);
            } else {
                return AppResponse.build(HttpStatus.NO_CONTENT).message("Sub accounts not found");
            }
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

}
