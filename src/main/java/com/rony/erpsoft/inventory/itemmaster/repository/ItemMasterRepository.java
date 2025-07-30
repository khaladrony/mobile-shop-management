package com.rony.erpsoft.inventory.itemmaster.repository;

import com.rony.erpsoft.inventory.itemmaster.model.ItemMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ItemMasterRepository extends JpaRepository<ItemMaster, Long> {

    @Query("SELECT max(itemCode) FROM ItemMaster ")
    String findLastItemCode();

    @Query("SELECT id as id, concat(itemName,' [',itemCode,']') as item_name_code, " +
            " itemCode as item_code, concat(category,' (',brand,')') as label  FROM ItemMaster WHERE active=true")
    List<Map<String, Object>> findActiveItemsForDropDown();
}
