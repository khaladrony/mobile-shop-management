package com.rony.erpsoft.inventory.itemmaster.mapper;

import com.rony.erpsoft.inventory.itemmaster.dto.ItemMasterRequestDTO;
import com.rony.erpsoft.inventory.itemmaster.dto.ItemMasterResponseDTO;
import com.rony.erpsoft.inventory.itemmaster.dto.MenuItemDTO;
import com.rony.erpsoft.inventory.itemmaster.model.ItemMaster;
import com.rony.erpsoft.utils.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ItemMasterMapper extends BaseMapper<ItemMasterResponseDTO, ItemMaster> {

    ItemMaster requestDTOToEntity(ItemMasterRequestDTO requestDTO);

    @Mapping(target = "name", source = "itemName")
    @Mapping(target = "specification", source = "description")
    @Mapping(target = "costPrice", source = "purchasePrice")
    @Mapping(target = "fileName", source = "fileName", qualifiedByName = "mapImageUrl")
    MenuItemDTO entityToMenuDTO(ItemMaster entity);

    @Named("mapImageUrl")
    default String mapImageUrl(String fileName) {
        return fileName != null ? "/uploads/inventory/" + fileName : null;
    }
}
