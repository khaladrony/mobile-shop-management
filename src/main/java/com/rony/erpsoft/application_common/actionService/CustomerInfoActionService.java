package com.rony.erpsoft.application_common.actionService;

import com.rony.erpsoft.application_common.model.BankInfo;
import com.rony.erpsoft.application_common.model.CustomerInfo;
import com.rony.erpsoft.application_common.service.BankInfoService;
import com.rony.erpsoft.application_common.service.CustomerInfoService;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CustomerInfoActionService {

    @Autowired
    ModelValidator modelValidator;
    @Autowired
    SessionService sessionService;
    @Autowired
    CustomerInfoService customerInfoService;

    public AppResponse execute(CustomerInfo customerInfo) {
        try {

            if (customerInfo.getId() == null) {

                customerInfo.setCustomerCode(customerInfoService.customerCodeGeneration());

                customerInfo.setOrganizationId(sessionService.getOrganizationId());
                customerInfo.setStatus(true);

                customerInfo.setCreatedBy(sessionService.getUserId());
                customerInfo.setCreateOn(new Date());
            } else {
                customerInfo.setUpdatedBy(sessionService.getUserId());
                customerInfo.setUpdatedOn(new Date());
            }

            if (modelValidator.isValid(customerInfo)) {
                if (customerInfoService.save(customerInfo).getId() > 0) {
                    return AppResponse.build(HttpStatus.OK).body(customerInfo);
                } else {
                    return AppResponse.build(HttpStatus.EXPECTATION_FAILED).message("Not created");
                }
            } else {
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message(modelValidator.validationMessage(customerInfo));
            }

        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }
}
