package com.rony.erpsoft.application_common.service;

import com.rony.erpsoft.application_common.model.EmployeeInfo;
import com.rony.erpsoft.application_common.repo.EmployeeInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeInfoService implements IEmployeeInfoService {

    @Autowired
    EmployeeInfoRepo employeeInfoRepo;
    @Autowired
    GeneralInfoCommonService generalInfoCommonService;

    @Override
    public List<EmployeeInfo> findAll() {
        return employeeInfoRepo.findAll();
    }

    @Override
    public EmployeeInfo findById(long id) {
        return employeeInfoRepo.findById(id);
    }

    @Override
    public EmployeeInfo save(EmployeeInfo employeeInfo) {
        return employeeInfoRepo.save(employeeInfo);
    }

    public List<Map<String, Object>> getEmployeeListForDropDown() {
        return employeeInfoRepo.getEmployeeListForDropDown();
    }

    public Map<String, Object> filter(int currentPage, int itemPerPage, Map<String, Object> params) throws Exception {

        String sqlStringOrderBy = " ORDER BY employee_code desc limit :offset, :limit ";
        String sqlStringCount = "SELECT count(id) FROM employee_info WHERE 1=1";
        String sqlStringRowData = "SELECT id, employee_code, employee_name, designation, email, phone, address, status FROM employee_info WHERE 1=1";

        params.put("sqlStringOrderBy", sqlStringOrderBy);
        params.put("sqlStringCount", sqlStringCount);
        params.put("sqlStringRowData", sqlStringRowData);


        return generalInfoCommonService.getFilterData(currentPage, itemPerPage, params);

    }


    public String employeeCodeGeneration() {
        String prefix = "EMP-";
        int length = 4;
        String lastBankAccountCode = employeeInfoRepo.findLastEmployeeCode();

        return generalInfoCommonService.autoCodeGeneration(prefix, length, lastBankAccountCode);
    }
}
