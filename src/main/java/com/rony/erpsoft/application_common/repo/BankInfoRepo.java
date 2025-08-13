package com.rony.erpsoft.application_common.repo;


import com.rony.erpsoft.application_common.model.BankInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankInfoRepo extends JpaRepository<BankInfo, Long> {

    BankInfo findById(long id);

    @Query("SELECT max(bankAccountCode) FROM BankInfo ")
    String findLastBankAccountCode();

    List<BankInfo> findAllByStatusTrueOrderByBankAccountName();
}
