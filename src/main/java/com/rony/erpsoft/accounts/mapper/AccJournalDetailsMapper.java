package com.rony.erpsoft.accounts.mapper;


import com.rony.erpsoft.accounts.dto.AccJournalDetailsDTO;
import com.rony.erpsoft.accounts.model.AccJournalDetails;
import com.rony.erpsoft.utils.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccJournalDetailsMapper extends BaseMapper<AccJournalDetailsDTO, AccJournalDetails> {
}
