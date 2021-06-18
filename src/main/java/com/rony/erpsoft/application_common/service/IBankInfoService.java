package com.rony.erpsoft.application_common.service;

import com.rony.erpsoft.application_common.model.BankInfo;

import java.util.List;

public interface IBankInfoService {
    List<BankInfo> findAll();

    BankInfo findById(long id);

    BankInfo save(BankInfo bankInfo);
}
