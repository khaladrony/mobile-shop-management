package com.rony.erpsoft.application_common.repo;

import com.rony.erpsoft.application_common.model.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerInfoRepo extends JpaRepository<CustomerInfo, Long> {

    CustomerInfo findById(long id);

    @Query("SELECT max(customerCode) FROM CustomerInfo ")
    String findLastCustomerCode();

    List<CustomerInfo> findAllByStatusTrueOrderByCustomerName();
}
