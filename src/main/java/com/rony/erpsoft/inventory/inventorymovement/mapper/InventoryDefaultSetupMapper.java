package com.rony.erpsoft.inventory.inventorymovement.mapper;

import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryDefaultSetupDTO;
import com.rony.erpsoft.inventory.inventorymovement.model.InventoryDefaultSetup;
import com.rony.erpsoft.utils.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryDefaultSetupMapper extends BaseMapper<InventoryDefaultSetupDTO, InventoryDefaultSetup> {
}
