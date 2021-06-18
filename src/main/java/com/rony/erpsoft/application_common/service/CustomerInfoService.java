package com.rony.erpsoft.application_common.service;

import com.rony.erpsoft.application_common.model.CustomerInfo;
import com.rony.erpsoft.application_common.repo.CustomerInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerInfoService implements ICustomerInfoService {

    @Autowired
    CustomerInfoRepo customerInfoRepo;
    @Autowired
    GeneralInfoCommonService generalInfoCommonService;

    @Override
    public List<CustomerInfo> findAll() {
        return customerInfoRepo.findAll();
    }

    @Override
    public CustomerInfo findById(long id) {
        return customerInfoRepo.findById(id);
    }

    @Override
    public CustomerInfo save(CustomerInfo customerInfo) {
        return customerInfoRepo.save(customerInfo);
    }

    public List<Map<String, Object>> getCustomerListForDropDown() {
        return customerInfoRepo.getCustomerListForDropDown();
    }

    public Map<String, Object> filter(int currentPage, int itemPerPage, Map<String, Object> params) throws Exception {

        String sqlStringOrderBy = " ORDER BY customer_code desc limit :offset, :limit ";
        String sqlStringCount = "SELECT count(id) FROM customer_info WHERE 1=1";
        String sqlStringRowData = "SELECT id, customer_code, customer_name, email, phone, address, status FROM customer_info WHERE 1=1";

        params.put("sqlStringOrderBy", sqlStringOrderBy);
        params.put("sqlStringCount", sqlStringCount);
        params.put("sqlStringRowData", sqlStringRowData);


        return generalInfoCommonService.getFilterData(currentPage, itemPerPage, params);

    }


    public String customerCodeGeneration() {
        String prefix = "CUS-";
        int length = 4;
        String lastBankAccountCode = customerInfoRepo.findLastCustomerCode();

        return generalInfoCommonService.autoCodeGeneration(prefix, length, lastBankAccountCode);
    }
}
