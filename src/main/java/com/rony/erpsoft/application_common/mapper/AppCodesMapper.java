package com.rony.erpsoft.application_common.mapper;

import com.rony.erpsoft.application_common.dto.AppCodesDTO;
import com.rony.erpsoft.application_common.model.AppCodes;
import com.rony.erpsoft.utils.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppCodesMapper extends BaseMapper<AppCodesDTO, AppCodes> {
}
