package com.rony.erpsoft.sales.service;

import com.rony.erpsoft.sales.dto.OrderDefaultSetupDTO;
import com.rony.erpsoft.sales.mapper.OrderDefaultSetupMapper;
import com.rony.erpsoft.sales.repository.OrderDefaultSetupRepository;
import com.rony.erpsoft.user_auth.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderDefaultSetupService {

    private final OrderDefaultSetupRepository repository;
    private final OrderDefaultSetupMapper mapper;
    private final SessionService sessionService;

    public OrderDefaultSetupDTO findByBranchCode(){
        return repository.findByBranchCode(sessionService.getBranch().getCode())
                .map(mapper::entityToDto)
                .orElse(null);
    }
}
