package com.rony.erpsoft.accounts.repo;

import com.rony.erpsoft.accounts.model.AccChartOfAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AccChartOfAccountsRepo extends JpaRepository<AccChartOfAccounts, Long> {

    List<AccChartOfAccounts> findAllByOrderByAccountsTypeAsc();

    AccChartOfAccounts findById(long id);

    AccChartOfAccounts findByAccountsCode(String code);

    AccChartOfAccounts findByAccountsType(String type);

    @Query("SELECT max(accountsCode) FROM AccChartOfAccounts WHERE accountsType=?1 ")
    String findLastAccountsCodeByAccountsType(String type);

    AccChartOfAccounts save(AccChartOfAccounts chartOfAccounts);

    @Query("SELECT id as id, accountsType as accounts_type, concat(accountsName,' (',accountsCode,')') as coa_code_name, " +
            "accountsSource as accounts_source, accountsUsage as accounts_usage " +
            "FROM AccChartOfAccounts WHERE is_active = 1 order by accountsType, accountsName")
    List<Map<String, Object>> findAllCOAListForDropDown();

    @Query("SELECT id as id, accountsType as accounts_type, concat(accountsName,' (',accountsCode,')') as coa_code_name FROM AccChartOfAccounts WHERE accountsUsage=?1 ")
    Map<String, Object> findChartOfAccountsByAccountsUsage(String type);
}
