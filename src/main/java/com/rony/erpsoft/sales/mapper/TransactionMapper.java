package com.rony.erpsoft.sales.mapper;

import com.rony.erpsoft.sales.dto.TransactionDTO;
import com.rony.erpsoft.sales.model.Transaction;
import com.rony.erpsoft.utils.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper extends BaseMapper<TransactionDTO, Transaction> {
}
