package com.rony.erpsoft.accounts.repo;

import com.rony.erpsoft.accounts.model.AccJournalDetails;
import com.rony.erpsoft.accounts.model.AccJournalMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface AccJournalMasterRepo extends JpaRepository<AccJournalMaster, Long> {
    List<AccJournalMaster> findAll();

    AccJournalMaster findById(long id);

    AccJournalMaster save(AccJournalMaster journalMaster);

    @Query("SELECT max(voucherNo) FROM AccJournalMaster WHERE voucherPrefix=?1 and year=?2 and month=?3 ")
    String findLastVoucherNoByVoucherPrefixAndYearAndMonth(String voucherPrefix, int year, int month);

    @Query("SELECT id as id, voucherNo as voucherNo, voucherDate as voucherDate, " +
            "particulars as particulars, amount as amount, paymentType as paymentType, status as status  FROM AccJournalMaster WHERE voucherType=?1  ")
    List<Map<String, Object>> getVoucherListByVoucherType(String voucherType);
}
