package com.rony.erpsoft.inventory.itemmaster.repository;

import com.rony.erpsoft.inventory.itemmaster.model.ItemMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemMasterRepository extends JpaRepository<ItemMaster, Long> {

    @Query("SELECT max(itemCode) FROM ItemMaster ")
    String findLastItemCode();
}
