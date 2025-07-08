package com.rony.erpsoft.inventory.itemmaster.mapper;

import com.rony.erpsoft.inventory.itemmaster.dto.ItemMasterRequestDTO;
import com.rony.erpsoft.inventory.itemmaster.dto.ItemMasterResponseDTO;
import com.rony.erpsoft.inventory.itemmaster.model.ItemMaster;
import com.rony.erpsoft.utils.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ItemMasterMapper extends BaseMapper<ItemMasterResponseDTO, ItemMaster> {

    @Override
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "organizationId", target = "organizationId"),
            @Mapping(source = "itemCode", target = "itemCode"),
            @Mapping(source = "itemName", target = "itemName"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "category", target = "category"),
            @Mapping(source = "brand", target = "brand"),
            @Mapping(source = "model", target = "model"),
            @Mapping(source = "color", target = "color"),
            @Mapping(source = "storage", target = "storage"),
            @Mapping(source = "unit", target = "unit"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "standardPrice", target = "standardPrice"),
            @Mapping(source = "standardCost", target = "standardCost"),
            @Mapping(source = "imei", target = "imei"),
            @Mapping(source = "active", target = "active"),
            @Mapping(source = "createdBy", target = "createdBy")
    })
    ItemMasterResponseDTO entityToDto(ItemMaster entity);

    @Override
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "organizationId", target = "organizationId"),
            @Mapping(source = "itemCode", target = "itemCode"),
            @Mapping(source = "itemName", target = "itemName"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "category", target = "category"),
            @Mapping(source = "brand", target = "brand"),
            @Mapping(source = "model", target = "model"),
            @Mapping(source = "color", target = "color"),
            @Mapping(source = "storage", target = "storage"),
            @Mapping(source = "unit", target = "unit"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "standardPrice", target = "standardPrice"),
            @Mapping(source = "standardCost", target = "standardCost"),
            @Mapping(source = "imei", target = "imei"),
            @Mapping(source = "active", target = "active"),
            @Mapping(source = "createdBy", target = "createdBy")
    })
    ItemMaster dtoToEntity(ItemMasterResponseDTO dto);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "organizationId", target = "organizationId"),
            @Mapping(source = "itemCode", target = "itemCode"),
            @Mapping(source = "itemName", target = "itemName"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "category", target = "category"),
            @Mapping(source = "brand", target = "brand"),
            @Mapping(source = "model", target = "model"),
            @Mapping(source = "color", target = "color"),
            @Mapping(source = "storage", target = "storage"),
            @Mapping(source = "unit", target = "unit"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "standardPrice", target = "standardPrice"),
            @Mapping(source = "standardCost", target = "standardCost"),
            @Mapping(source = "imei", target = "imei"),
            @Mapping(source = "active", target = "active"),
            @Mapping(source = "createdBy", target = "createdBy")
    })
    ItemMaster requestDTOToEntity(ItemMasterRequestDTO requestDTO);
}
