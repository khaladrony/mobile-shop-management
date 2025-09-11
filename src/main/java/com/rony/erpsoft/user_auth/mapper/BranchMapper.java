package com.rony.erpsoft.user_auth.mapper;

import com.rony.erpsoft.user_auth.dto.BranchDTO;
import com.rony.erpsoft.user_auth.model.Branch;
import com.rony.erpsoft.utils.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BranchMapper extends BaseMapper<BranchDTO, Branch> {
}
