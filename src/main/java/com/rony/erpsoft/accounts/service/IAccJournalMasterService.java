package com.rony.erpsoft.accounts.service;

import com.rony.erpsoft.accounts.model.AccJournalMaster;

import java.util.List;

public interface IAccJournalMasterService {
    List<AccJournalMaster> findAll();

    AccJournalMaster findById(long id);

    AccJournalMaster save(AccJournalMaster journalMaster);

}
