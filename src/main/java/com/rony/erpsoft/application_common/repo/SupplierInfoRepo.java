package com.rony.erpsoft.application_common.repo;

import com.rony.erpsoft.application_common.model.SupplierInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface SupplierInfoRepo extends JpaRepository<SupplierInfo, Long> {
    List<SupplierInfo> findAll();

    SupplierInfo findById(long id);

    SupplierInfo save(SupplierInfo supplierInfo);

    @Query("SELECT max(supplierCode) FROM SupplierInfo ")
    String findLastSupplierCode();

    @Query("SELECT id as id,  concat(supplierName,' (',supplierCode,')') as name " +
            "FROM SupplierInfo WHERE status = 1 order by supplierName")
    List<Map<String, Object>> getSupplierListForDropDown();
}
