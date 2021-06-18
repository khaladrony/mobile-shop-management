package com.rony.erpsoft.accounts.service;

import com.rony.erpsoft.accounts.model.AccChartOfAccounts;
import com.rony.erpsoft.accounts.model.enums.AccountsType;
import com.rony.erpsoft.accounts.repo.AccChartOfAccountsRepo;
import com.rony.erpsoft.accounts.repo.AccJournalDetailsRepo;
import com.rony.erpsoft.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccChartOfAccountsService implements IAccChartOfAccountsService {

    @Autowired
    AccChartOfAccountsRepo accChartOfAccountsRepo;
    @Autowired
    NamedParameterJdbcTemplate nDB;
    @Autowired
    AccJournalDetailsRepo accJournalDetailsRepo;


    @Override
    public List<AccChartOfAccounts> findAll() {
        return accChartOfAccountsRepo.findAllByOrderByAccountsTypeAsc();
    }

    @Override
    public AccChartOfAccounts findById(long id) {
        return accChartOfAccountsRepo.findById(id);
    }

    @Override
    public AccChartOfAccounts findByAccountsCode(String code) {
        return accChartOfAccountsRepo.findByAccountsCode(code);
    }

    @Override
    public AccChartOfAccounts findByAccountsType(String type) {
        return accChartOfAccountsRepo.findByAccountsType(type);
    }

    @Override
    public String findLastAccountsCodeByAccountsType(String type) {
        return accChartOfAccountsRepo.findLastAccountsCodeByAccountsType(type);
    }

    @Override
    public AccChartOfAccounts save(AccChartOfAccounts chartOfAccounts) {
        return accChartOfAccountsRepo.save(chartOfAccounts);
    }

    @Override
    public List<Map<String, Object>> findAllCOAListForDropDown() {
        return accChartOfAccountsRepo.findAllCOAListForDropDown();
    }

    public Map<String, Object> findChartOfAccountsByAccountsUsage(String type) {
        return accChartOfAccountsRepo.findChartOfAccountsByAccountsUsage(type);
    }

    public String accountsCodeGenerate(String accountsType) {
        int codeLength = 6;
        String accountsCode = "";
        int accountsCodeInt = 0;

        String availableAccountCode = findLastAccountsCodeByAccountsType(accountsType);
        if (availableAccountCode == null) {
            for (AccountsType type : AccountsType.list()) {
                if (accountsType.equals(type.displayName())) {
                    String accountsCodePad = AppUtil.leftPad(type.code(), codeLength, '0');
                    accountsCodeInt = Integer.parseInt(accountsCodePad) + 1;
                    accountsCode = String.valueOf(accountsCodeInt);
                    break;
                }
            }
        } else {
            accountsCodeInt = Integer.parseInt(availableAccountCode) + 1;
            accountsCode = String.valueOf(accountsCodeInt);
        }

        return accountsCode;
    }

    public Map<String, Object> filter(int currentPage, int itemPerPage, Map<String, Object> request) throws  Exception{

        request.put("offset", (currentPage - 1)*itemPerPage );
        request.put("limit", itemPerPage);

        Map<String, Object> data = new HashMap<>();
        data.put("itemCount", count(request));
        data.put("items", rowsData(request));

        return data;
    }

    public List<Map<String, Object>> rowsData(Map<String, Object> params) {

        StringBuilder sql;
        sql = voucherListQueryString(params, false);
        sql.append(" ORDER BY accounts_code, accounts_type limit :offset, :limit ");

        return nDB.queryForList(sql.toString(), params);
    }

    public Long count(Map<String, Object> params){

        StringBuilder sql;
        sql = voucherListQueryString(params, true);

        return nDB.queryForObject(sql.toString(), params, Long.class);
    }

    public StringBuilder voucherListQueryString(Map<String, Object> params, boolean isCount){

        StringBuilder sql = new StringBuilder();

        if(isCount){
            sql.append("SELECT count(id) FROM acc_chart_of_accounts WHERE 1=1");
        } else {
            sql.append("SELECT id, accounts_code, accounts_name, accounts_type, accounts_usage, " +
                    "accounts_source, is_active FROM acc_chart_of_accounts WHERE 1=1");
        }

        return sql;
    }

    public int getVoucherCountByChartOfAccountsId(long chartOfAccountsId){
        return accJournalDetailsRepo.getVoucherCountByChartOfAccountsId(chartOfAccountsId);
    }

}


