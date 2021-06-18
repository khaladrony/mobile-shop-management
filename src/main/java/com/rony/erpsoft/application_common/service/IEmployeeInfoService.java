package com.rony.erpsoft.application_common.service;

import com.rony.erpsoft.application_common.model.EmployeeInfo;

import java.util.List;

public interface IEmployeeInfoService {

    List<EmployeeInfo> findAll();

    EmployeeInfo findById(long id);

    EmployeeInfo save(EmployeeInfo employeeInfo);
}
