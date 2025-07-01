package com.rony.erpsoft.inventory.service;

import com.rony.erpsoft.inventory.model.ItemMaster;

import java.util.List;

public interface ItemMasterService {

    List<ItemMaster> findAll();

    ItemMaster findById(long id);

    ItemMaster save(ItemMaster itemMaster);
}
