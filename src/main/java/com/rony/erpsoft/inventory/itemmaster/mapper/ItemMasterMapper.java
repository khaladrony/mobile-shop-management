package com.rony.erpsoft.inventory.itemmaster.mapper;

import com.rony.erpsoft.inventory.itemmaster.dto.ItemMasterRequestDTO;
import com.rony.erpsoft.inventory.itemmaster.dto.ItemMasterResponseDTO;
import com.rony.erpsoft.inventory.itemmaster.model.ItemMaster;
import com.rony.erpsoft.utils.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMasterMapper extends BaseMapper<ItemMasterResponseDTO, ItemMaster> {

    ItemMaster requestDTOToEntity(ItemMasterRequestDTO requestDTO);
}
