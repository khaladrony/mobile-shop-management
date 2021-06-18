package com.rony.erpsoft.application_common.actionService;

import com.rony.erpsoft.application_common.model.CustomerInfo;
import com.rony.erpsoft.application_common.model.SupplierInfo;
import com.rony.erpsoft.application_common.service.CustomerInfoService;
import com.rony.erpsoft.application_common.service.SupplierInfoService;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SupplierInfoActionService {

    @Autowired
    ModelValidator modelValidator;
    @Autowired
    SessionService sessionService;
    @Autowired
    SupplierInfoService supplierInfoService;

    public AppResponse execute(SupplierInfo supplierInfo) {
        try {

            if (supplierInfo.getId() == null) {

                supplierInfo.setSupplierCode(supplierInfoService.supplierCodeGeneration());

                supplierInfo.setOrganizationId(sessionService.getOrganizationId());
                supplierInfo.setStatus(true);

                supplierInfo.setCreatedBy(sessionService.getUserId());
                supplierInfo.setCreateOn(new Date());
            } else {
                supplierInfo.setUpdatedBy(sessionService.getUserId());
                supplierInfo.setUpdatedOn(new Date());
            }

            if (modelValidator.isValid(supplierInfo)) {
                if (supplierInfoService.save(supplierInfo).getId() > 0) {
                    return AppResponse.build(HttpStatus.OK).body(supplierInfo);
                } else {
                    return AppResponse.build(HttpStatus.EXPECTATION_FAILED).message("Not created");
                }
            } else {
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message(modelValidator.validationMessage(supplierInfo));
            }

        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }
}
