package com.rony.erpsoft.accounts.service;

import com.rony.erpsoft.accounts.model.AccChartOfAccounts;

import java.util.List;
import java.util.Map;

public interface IAccChartOfAccountsService {
    List<AccChartOfAccounts> findAll();

    AccChartOfAccounts findById(long id);

    AccChartOfAccounts findByAccountsCode(String code);

    AccChartOfAccounts findByAccountsType(String type);

    String findLastAccountsCodeByAccountsType(String type);

    AccChartOfAccounts save(AccChartOfAccounts chartOfAccounts);

    List<Map<String, Object>> findAllCOAListForDropDown();
}
