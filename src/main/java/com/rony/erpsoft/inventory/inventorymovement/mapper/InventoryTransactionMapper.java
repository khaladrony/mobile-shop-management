package com.rony.erpsoft.inventory.inventorymovement.mapper;

import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryTransactionDTO;
import com.rony.erpsoft.inventory.inventorymovement.model.InventoryTransaction;
import com.rony.erpsoft.utils.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryTransactionMapper extends BaseMapper<InventoryTransactionDTO, InventoryTransaction> {
}
