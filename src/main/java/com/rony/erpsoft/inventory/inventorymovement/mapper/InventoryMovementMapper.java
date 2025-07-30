package com.rony.erpsoft.inventory.inventorymovement.mapper;

import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryMovementRequestDTO;
import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryMovementResponseDTO;
import com.rony.erpsoft.inventory.inventorymovement.model.InventoryMovement;
import com.rony.erpsoft.utils.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMovementMapper extends BaseMapper<InventoryMovementResponseDTO, InventoryMovement> {

    @Mapping(target = "sign", expression = "java(request.getAction() != null ? request.getAction().getSign() : 0)")
    @Mapping(target = "year", expression = "java(request.getTransactionDate() != null ? request.getTransactionDate().getYear() : 0)")
    @Mapping(target = "month", expression = "java(request.getTransactionDate() != null ? request.getTransactionDate().getMonthValue() : 0)")
    InventoryMovement requestDTOToEntity(InventoryMovementRequestDTO request);


}
