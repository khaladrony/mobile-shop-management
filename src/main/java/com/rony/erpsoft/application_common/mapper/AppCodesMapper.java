package com.rony.erpsoft.application_common.mapper;

import com.rony.erpsoft.application_common.dto.AppCodesDTO;
import com.rony.erpsoft.application_common.model.AppCodes;
import com.rony.erpsoft.utils.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface AppCodesMapper extends BaseMapper<AppCodesDTO, AppCodes> {

    @Override
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "organizationId", target = "organizationId"),
            @Mapping(source = "createdBy", target = "createdBy"),
            @Mapping(source = "xtype", target = "xtype"),
            @Mapping(source = "xcode", target = "xcode"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "active", target = "active")
    })
    AppCodesDTO entityToDto(AppCodes entity);

    @Override
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "organizationId", target = "organizationId"),
            @Mapping(source = "createdBy", target = "createdBy"),
            @Mapping(source = "xtype", target = "xtype"),
            @Mapping(source = "xcode", target = "xcode"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "active", target = "active")
    })
    AppCodes dtoToEntity(AppCodesDTO dto);

}
