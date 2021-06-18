package com.rony.erpsoft.application_common.actionService;

import com.rony.erpsoft.application_common.model.CustomerInfo;
import com.rony.erpsoft.application_common.model.EmployeeInfo;
import com.rony.erpsoft.application_common.service.CustomerInfoService;
import com.rony.erpsoft.application_common.service.EmployeeInfoService;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmployeeInfoActionService {

    @Autowired
    ModelValidator modelValidator;
    @Autowired
    SessionService sessionService;
    @Autowired
    EmployeeInfoService employeeInfoService;

    public AppResponse execute(EmployeeInfo employeeInfo) {
        try {

            if (employeeInfo.getId() == null) {

                employeeInfo.setEmployeeCode(employeeInfoService.employeeCodeGeneration());

                employeeInfo.setOrganizationId(sessionService.getOrganizationId());
                employeeInfo.setStatus(true);

                employeeInfo.setCreatedBy(sessionService.getUserId());
                employeeInfo.setCreateOn(new Date());
            } else {
                employeeInfo.setUpdatedBy(sessionService.getUserId());
                employeeInfo.setUpdatedOn(new Date());
            }

            if (modelValidator.isValid(employeeInfo)) {
                if (employeeInfoService.save(employeeInfo).getId() > 0) {
                    return AppResponse.build(HttpStatus.OK).body(employeeInfo);
                } else {
                    return AppResponse.build(HttpStatus.EXPECTATION_FAILED).message("Not created");
                }
            } else {
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message(modelValidator.validationMessage(employeeInfo));
            }

        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }
}
