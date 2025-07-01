package com.rony.erpsoft.application_common.service;

import com.rony.erpsoft.accounts.dto.SubCOADropdownDTO;
import com.rony.erpsoft.application_common.model.BankInfo;
import com.rony.erpsoft.application_common.repo.BankInfoRepo;
import com.rony.erpsoft.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BankInfoService implements IBankInfoService {

    @Autowired
    BankInfoRepo bankInfoRepo;
    @Autowired
    GeneralInfoCommonService generalInfoCommonService;


    @Override
    public List<BankInfo> findAll() {
        return bankInfoRepo.findAll();
    }

    @Override
    public BankInfo findById(long id) {
        return bankInfoRepo.findById(id);
    }

    @Override
    public BankInfo save(BankInfo bankInfo) {
        return bankInfoRepo.save(bankInfo);
    }

    public List<SubCOADropdownDTO> getBankAccountListForDropDown() {
        return bankInfoRepo.getBankAccountListForDropDown();
    }

    public Map<String, Object> filter(int currentPage, int itemPerPage, Map<String, Object> params) throws Exception {

        String sqlStringOrderBy = " ORDER BY bank_account_code desc limit :offset, :limit ";
        String sqlStringCount = "SELECT count(id) FROM bank_info WHERE 1=1";
        String sqlStringRowData = "SELECT id, bank_account_code, bank_account_name, bank_account_no, phone, address, status FROM bank_info WHERE 1=1";

        params.put("sqlStringOrderBy", sqlStringOrderBy);
        params.put("sqlStringCount", sqlStringCount);
        params.put("sqlStringRowData", sqlStringRowData);


        return generalInfoCommonService.getFilterData(currentPage, itemPerPage, params);

    }


    public String bankCodeGeneration() {
        String prefix = "BA-";
        int length = 4;
        String lastBankAccountCode = bankInfoRepo.findLastBankAccountCode();

        return generalInfoCommonService.autoCodeGeneration(prefix, length, lastBankAccountCode);
    }
}
