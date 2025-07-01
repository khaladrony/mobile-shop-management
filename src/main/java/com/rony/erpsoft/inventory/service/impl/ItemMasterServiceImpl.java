package com.rony.erpsoft.inventory.service.impl;

import com.rony.erpsoft.inventory.model.ItemMaster;
import com.rony.erpsoft.inventory.repository.ItemMasterRepository;
import com.rony.erpsoft.inventory.service.ItemMasterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemMasterServiceImpl implements ItemMasterService {

    private final ItemMasterRepository itemMasterRepository;

    @Override
    public List<ItemMaster> findAll() {
        return List.of();
    }

    @Override
    public ItemMaster findById(long id) {
        return null;
    }

    @Override
    public ItemMaster save(ItemMaster itemMaster) {
        return itemMasterRepository.save(itemMaster);
    }
}
