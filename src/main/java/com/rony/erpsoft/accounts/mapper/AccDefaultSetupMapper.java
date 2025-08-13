package com.rony.erpsoft.accounts.mapper;

import com.rony.erpsoft.accounts.dto.AccDefaultSetupDTO;
import com.rony.erpsoft.accounts.model.AccDefaultSetup;
import com.rony.erpsoft.utils.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccDefaultSetupMapper extends BaseMapper<AccDefaultSetupDTO, AccDefaultSetup> {
}
