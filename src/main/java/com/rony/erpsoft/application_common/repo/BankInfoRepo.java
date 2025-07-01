package com.rony.erpsoft.application_common.repo;


import com.rony.erpsoft.accounts.dto.SubCOADropdownDTO;
import com.rony.erpsoft.application_common.model.BankInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BankInfoRepo extends JpaRepository<BankInfo, Long> {
//    List<BankInfo> findAll();

    BankInfo findById(long id);

//    BankInfo save(BankInfo bankInfo);

    @Query("SELECT max(bankAccountCode) FROM BankInfo ")
    String findLastBankAccountCode();

    @Query("SELECT id as id,  concat(bankAccountName,' (',bankAccountNo,')') as name " +
            "FROM BankInfo WHERE status = true order by bankAccountName")
    List<SubCOADropdownDTO> getBankAccountListForDropDown();
}
