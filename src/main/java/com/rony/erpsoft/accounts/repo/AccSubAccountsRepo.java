package com.rony.erpsoft.accounts.repo;

import com.rony.erpsoft.accounts.model.AccSubAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccSubAccountsRepo extends JpaRepository<AccSubAccounts, Long> {

    @Query(" FROM AccSubAccounts WHERE chartOfAccountsId=?1 ")
    List<AccSubAccounts> findAllByCoa(Long coaId);

    AccSubAccounts findById(long id);

    AccSubAccounts findByIdAndChartOfAccountsId(long id, long coaId);

    @Query("SELECT max(subAccountsCode) FROM AccSubAccounts WHERE chartOfAccountsId=?1 ")
    String findLastSubAccountsCodeByCoaId(Long coaId);

    List<AccSubAccounts> findAllByStatusTrueAndChartOfAccountsId(Long chartOfAccountsId);
}
