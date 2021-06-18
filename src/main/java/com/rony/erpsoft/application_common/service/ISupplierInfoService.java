package com.rony.erpsoft.application_common.service;

import com.rony.erpsoft.application_common.model.SupplierInfo;

import java.util.List;

public interface ISupplierInfoService {

    List<SupplierInfo> findAll();

    SupplierInfo findById(long id);

    SupplierInfo save(SupplierInfo supplierInfo);
}
