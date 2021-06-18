package com.rony.erpsoft.accounts.actionService.accSubAccounts;

import com.rony.erpsoft.accounts.actionService.IService;
import com.rony.erpsoft.accounts.model.AccSubAccounts;
import com.rony.erpsoft.accounts.service.AccSubAccountsService;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SaveSubAccountsActionService implements IService<AccSubAccounts> {

    @Autowired
    AccSubAccountsService accSubAccountsService;
    @Autowired
    ModelValidator modelValidator;
    @Autowired
    SessionService sessionService;

    @Override
    public AppResponse execute(AccSubAccounts subAccounts) {
        try {
            if (subAccounts.getId() == null) {
                String subAccountsCode = accSubAccountsService.subAccountsCodeGenerate(subAccounts.getChartOfAccountsId(), subAccounts.getChartOfAccountsCode());
                subAccounts.setChartOfAccountsId(subAccounts.getChartOfAccountsId());
                subAccounts.setSubAccountsCode(subAccountsCode);
                subAccounts.setSubAccountsName(subAccounts.getSubAccountsName());
                subAccounts.setStatus(subAccounts.isStatus());
                subAccounts.setOrganizationId(sessionService.getOrganizationId());
                subAccounts.setCreatedBy(sessionService.getUserId());
                subAccounts.setCreateOn(new Date());
            } else {
                subAccounts.setStatus(subAccounts.isStatus());
                subAccounts.setUpdatedBy(sessionService.getUserId());
                subAccounts.setUpdatedOn(new Date());
            }

            if (modelValidator.isValid(subAccounts)) {
                if (accSubAccountsService.save(subAccounts).getId() > 0) {
                    return AppResponse.build(HttpStatus.OK).body(subAccounts);
                } else {
                    return AppResponse.build(HttpStatus.EXPECTATION_FAILED).message("Not created");
                }
            } else {
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message(modelValidator.validationMessage(subAccounts));
            }
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }
}
