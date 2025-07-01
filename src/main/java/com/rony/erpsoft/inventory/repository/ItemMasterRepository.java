package com.rony.erpsoft.inventory.repository;

import com.rony.erpsoft.inventory.model.ItemMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemMasterRepository extends JpaRepository<ItemMaster, Long> {
}
