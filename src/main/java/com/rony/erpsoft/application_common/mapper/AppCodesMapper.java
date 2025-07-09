package com.rony.erpsoft.application_common.mapper;

import com.rony.erpsoft.application_common.dto.AppCodesDTO;
import com.rony.erpsoft.application_common.model.AppCodes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppCodesMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "organizationId", target = "organizationId"),
            @Mapping(source = "createdBy", target = "createdBy"),
            @Mapping(source = "xtype", target = "xtype"),
            @Mapping(source = "xcode", target = "xcode"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "active", target = "active")
    })
    AppCodesDTO toDto(AppCodes entity);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "organizationId", target = "organizationId"),
            @Mapping(source = "createdBy", target = "createdBy"),
            @Mapping(source = "xtype", target = "xtype"),
            @Mapping(source = "xcode", target = "xcode"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "active", target = "active")
    })
    AppCodes toEntity(AppCodesDTO dto);

    List<AppCodesDTO> toDtoList(List<AppCodes> entities);

    List<AppCodes> toEntityList(List<AppCodesDTO> dtos);
}
