package com.rony.erpsoft.accounts.service;

import com.rony.erpsoft.accounts.dto.SubCOADropdownDTO;
import com.rony.erpsoft.accounts.model.AccSubAccounts;

import java.util.List;
import java.util.Map;

public interface IAccSubAccountsService {
    List<AccSubAccounts> findAll();

    List<AccSubAccounts> findAllByCoa(Long coaId);

    AccSubAccounts findById(long id);

    String findLastSubAccountsCodeByCoaId(Long coaId);

    AccSubAccounts save(AccSubAccounts subAccounts);

    List<SubCOADropdownDTO> findAllSubAccountListByAccountsSource(String accountsSource, Long chartOfAccountsId);
}
