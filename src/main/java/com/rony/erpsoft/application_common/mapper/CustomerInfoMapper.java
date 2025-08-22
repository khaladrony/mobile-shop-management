package com.rony.erpsoft.application_common.mapper;

import com.rony.erpsoft.application_common.dto.CustomerSearchDTO;
import com.rony.erpsoft.application_common.model.CustomerInfo;
import com.rony.erpsoft.utils.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerInfoMapper extends BaseMapper<CustomerSearchDTO, CustomerInfo> {
}
