package com.rony.erpsoft.sales.mapper;

import com.rony.erpsoft.sales.dto.OrderDefaultSetupDTO;
import com.rony.erpsoft.sales.model.OrderDefaultSetup;
import com.rony.erpsoft.utils.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDefaultSetupMapper extends BaseMapper<OrderDefaultSetupDTO, OrderDefaultSetup> {
}
