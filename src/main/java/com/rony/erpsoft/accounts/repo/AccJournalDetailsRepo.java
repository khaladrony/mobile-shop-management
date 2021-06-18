package com.rony.erpsoft.accounts.repo;

import com.rony.erpsoft.accounts.model.AccJournalDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccJournalDetailsRepo  extends JpaRepository<AccJournalDetails, Long> {

    List<AccJournalDetails> findAllByJournalMasterId(long id);

    @Query("SELECT count(id) FROM AccJournalDetails WHERE chartOfAccountsId=?1 ")
    int getVoucherCountByChartOfAccountsId(long chartOfAccountsId);
}
