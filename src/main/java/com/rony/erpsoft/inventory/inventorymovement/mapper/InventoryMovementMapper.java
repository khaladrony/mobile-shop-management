package com.rony.erpsoft.inventory.inventorymovement.mapper;

import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryMovementRequestDTO;
import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryMovementResponseDTO;
import com.rony.erpsoft.inventory.inventorymovement.model.InventoryMovement;
import com.rony.erpsoft.utils.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMovementMapper extends BaseMapper<InventoryMovementResponseDTO, InventoryMovement> {

    InventoryMovement requestDTOToEntity(InventoryMovementRequestDTO movementRequestDTO);
}
