package com.rony.erpsoft.accounts.repo;

import com.rony.erpsoft.accounts.model.AccSubAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface AccSubAccountsRepo extends JpaRepository<AccSubAccounts, Long> {
    List<AccSubAccounts> findAll();

    @Query(" FROM AccSubAccounts WHERE chartOfAccountsId=?1 ")
    List<AccSubAccounts> findAllByCoa(Long coaId);

    AccSubAccounts findById(long id);

    AccSubAccounts findByIdAndChartOfAccountsId(long id, long coaId);

    @Query("SELECT max(subAccountsCode) FROM AccSubAccounts WHERE chartOfAccountsId=?1 ")
    String findLastSubAccountsCodeByCoaId(Long coaId);

    AccSubAccounts save(AccSubAccounts subAccounts);

    @Query("SELECT id as id, concat(subAccountsName,' (',subAccountsCode,')') as name, chartOfAccountsId as chartOfAccountsId " +
            " FROM AccSubAccounts WHERE status = 1 and chartOfAccountsId=?1")
    List<Map<String, Object>> findAllSubCOAListForDropDown(Long coaId);
}
