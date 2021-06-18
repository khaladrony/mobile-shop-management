package com.rony.erpsoft.accounts.actionService.chartOfAccounts;

import com.rony.erpsoft.accounts.model.AccChartOfAccounts;
import com.rony.erpsoft.accounts.service.AccChartOfAccountsService;
import com.rony.erpsoft.configuration.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllChartOfAccountsActionService {
    @Autowired
    AccChartOfAccountsService accChartOfAccountsService;


    public AppResponse execute(){
        try{
            List<AccChartOfAccounts> list = accChartOfAccountsService.findAll();
            if (!list.isEmpty()) {
                return AppResponse.build(HttpStatus.OK).body(list);
            } else {
                return AppResponse.build(HttpStatus.NO_CONTENT).message("No chart of accounts found");
            }
        } catch (Exception ex){
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }
}
