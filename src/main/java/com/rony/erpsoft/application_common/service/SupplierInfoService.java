package com.rony.erpsoft.application_common.service;

import com.rony.erpsoft.application_common.model.SupplierInfo;
import com.rony.erpsoft.application_common.repo.SupplierInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SupplierInfoService implements ISupplierInfoService {

    @Autowired
    SupplierInfoRepo supplierInfoRepo;
    @Autowired
    GeneralInfoCommonService generalInfoCommonService;

    @Override
    public List<SupplierInfo> findAll() {
        return supplierInfoRepo.findAll();
    }

    @Override
    public SupplierInfo findById(long id) {
        return supplierInfoRepo.findById(id);
    }

    @Override
    public SupplierInfo save(SupplierInfo supplierInfo) {
        return supplierInfoRepo.save(supplierInfo);
    }

    public List<Map<String, Object>> getSupplierListForDropDown() {
        return supplierInfoRepo.getSupplierListForDropDown();
    }

    public Map<String, Object> filter(int currentPage, int itemPerPage, Map<String, Object> params) throws Exception {

        String sqlStringOrderBy = " ORDER BY supplier_code desc limit :offset, :limit ";
        String sqlStringCount = "SELECT count(id) FROM supplier_info WHERE 1=1";
        String sqlStringRowData = "SELECT id, supplier_code, supplier_name, email, phone, address, status FROM supplier_info WHERE 1=1";

        params.put("sqlStringOrderBy", sqlStringOrderBy);
        params.put("sqlStringCount", sqlStringCount);
        params.put("sqlStringRowData", sqlStringRowData);


        return generalInfoCommonService.getFilterData(currentPage, itemPerPage, params);

    }


    public String supplierCodeGeneration() {
        String prefix = "SUP-";
        int length = 4;
        String lastBankAccountCode = supplierInfoRepo.findLastSupplierCode();

        return generalInfoCommonService.autoCodeGeneration(prefix, length, lastBankAccountCode);
    }
}
