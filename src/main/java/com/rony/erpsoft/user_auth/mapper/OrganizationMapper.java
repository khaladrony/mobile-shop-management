package com.rony.erpsoft.user_auth.mapper;

import com.rony.erpsoft.user_auth.dto.OrganizationDTO;
import com.rony.erpsoft.user_auth.model.Organization;
import com.rony.erpsoft.utils.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationMapper extends BaseMapper<OrganizationDTO, Organization> {
}
