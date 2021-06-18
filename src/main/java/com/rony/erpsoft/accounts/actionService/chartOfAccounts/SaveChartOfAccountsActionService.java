package com.rony.erpsoft.accounts.actionService.chartOfAccounts;

import com.rony.erpsoft.accounts.actionService.IService;
import com.rony.erpsoft.accounts.model.AccChartOfAccounts;
import com.rony.erpsoft.accounts.service.AccChartOfAccountsService;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SaveChartOfAccountsActionService implements IService<AccChartOfAccounts> {

    @Autowired
    AccChartOfAccountsService accChartOfAccountsService;
    @Autowired
    ModelValidator modelValidator;
    @Autowired
    SessionService sessionService;

    public AppResponse execute(AccChartOfAccounts chartOfAccounts) {
        try {
            if (chartOfAccounts.getId() == null) {
                String accountsCode = accChartOfAccountsService.accountsCodeGenerate(chartOfAccounts.getAccountsType());
                chartOfAccounts.setAccountsCode(accountsCode);
                chartOfAccounts.setOrganizationId(sessionService.getOrganizationId());
                chartOfAccounts.setActive(chartOfAccounts.isActive());
                chartOfAccounts.setLeaf(chartOfAccounts.isLeaf());
                chartOfAccounts.setCreatedBy(sessionService.getUserId());
                chartOfAccounts.setCreateOn(new Date());
            } else {
                chartOfAccounts.setActive(chartOfAccounts.isActive());
                chartOfAccounts.setUpdatedBy(sessionService.getUserId());
                chartOfAccounts.setUpdatedOn(new Date());
            }

            if (modelValidator.isValid(chartOfAccounts)) {
                if (accChartOfAccountsService.save(chartOfAccounts).getId() > 0) {
                    return AppResponse.build(HttpStatus.OK).body(chartOfAccounts);
                } else {
                    return AppResponse.build(HttpStatus.EXPECTATION_FAILED).message("Not created");
                }
            } else {
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message(modelValidator.validationMessage(chartOfAccounts));
            }
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }
}
