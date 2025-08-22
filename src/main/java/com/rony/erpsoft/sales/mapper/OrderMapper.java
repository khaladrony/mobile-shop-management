package com.rony.erpsoft.sales.mapper;

import com.rony.erpsoft.sales.dto.OrderRequestDTO;
import com.rony.erpsoft.sales.dto.OrderResponseDTO;
import com.rony.erpsoft.sales.model.Order;
import com.rony.erpsoft.utils.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<OrderResponseDTO, Order> {

    Order requestDTOToEntity(OrderRequestDTO requestDTO);
}
