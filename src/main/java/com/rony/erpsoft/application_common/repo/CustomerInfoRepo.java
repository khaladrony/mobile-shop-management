package com.rony.erpsoft.application_common.repo;

import com.rony.erpsoft.application_common.model.BankInfo;
import com.rony.erpsoft.application_common.model.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface CustomerInfoRepo extends JpaRepository<CustomerInfo, Long> {
    List<CustomerInfo> findAll();

    CustomerInfo findById(long id);

    CustomerInfo save(CustomerInfo customerInfo);

    @Query("SELECT max(customerCode) FROM CustomerInfo ")
    String findLastCustomerCode();

    @Query("SELECT id as id,  concat(customerName,' (',customerCode,')') as name " +
            "FROM CustomerInfo WHERE status = 1 order by customerName")
    List<Map<String, Object>> getCustomerListForDropDown();
}
