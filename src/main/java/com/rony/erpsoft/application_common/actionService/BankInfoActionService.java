package com.rony.erpsoft.application_common.actionService;

import com.rony.erpsoft.application_common.model.BankInfo;
import com.rony.erpsoft.application_common.service.BankInfoService;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BankInfoActionService {

    @Autowired
    ModelValidator modelValidator;
    @Autowired
    SessionService sessionService;
    @Autowired
    BankInfoService bankInfoService;

    public AppResponse execute(BankInfo bankInfo) {
        try {

            if (bankInfo.getId() == null) {

                bankInfo.setBankAccountCode(bankInfoService.bankCodeGeneration());

                bankInfo.setOrganizationId(sessionService.getOrganizationId());
                bankInfo.setStatus(true);

                bankInfo.setCreatedBy(sessionService.getUserId());
                bankInfo.setCreateOn(new Date());
            } else {
                bankInfo.setUpdatedBy(sessionService.getUserId());
                bankInfo.setUpdatedOn(new Date());
            }

            if (modelValidator.isValid(bankInfo)) {
                if (bankInfoService.save(bankInfo).getId() > 0) {
                    return AppResponse.build(HttpStatus.OK).body(bankInfo);
                } else {
                    return AppResponse.build(HttpStatus.EXPECTATION_FAILED).message("Not created");
                }
            } else {
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message(modelValidator.validationMessage(bankInfo));
            }

        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }
}
