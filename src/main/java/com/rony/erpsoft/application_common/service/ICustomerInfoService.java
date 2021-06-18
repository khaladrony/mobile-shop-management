package com.rony.erpsoft.application_common.service;

import com.rony.erpsoft.application_common.model.CustomerInfo;

import java.util.List;

public interface ICustomerInfoService {
    List<CustomerInfo> findAll();

    CustomerInfo findById(long id);

    CustomerInfo save(CustomerInfo customerInfo);
}
