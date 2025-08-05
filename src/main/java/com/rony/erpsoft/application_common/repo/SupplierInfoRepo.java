package com.rony.erpsoft.application_common.repo;

import com.rony.erpsoft.application_common.model.SupplierInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupplierInfoRepo extends JpaRepository<SupplierInfo, Long> {

    SupplierInfo findById(long id);

    @Query("SELECT max(supplierCode) FROM SupplierInfo ")
    String findLastSupplierCode();

    List<SupplierInfo> findAllByStatusTrueOrderBySupplierName();
}
