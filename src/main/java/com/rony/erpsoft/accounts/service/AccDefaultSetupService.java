package com.rony.erpsoft.accounts.service;

import com.rony.erpsoft.accounts.dto.AccDefaultSetupDTO;
import com.rony.erpsoft.accounts.mapper.AccDefaultSetupMapper;
import com.rony.erpsoft.accounts.repo.AccDefaultSetupRepo;
import com.rony.erpsoft.user_auth.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccDefaultSetupService {

    private final AccDefaultSetupRepo accDefaultSetupRepo;
    private final AccDefaultSetupMapper accDefaultSetupMapper;
    private final SessionService sessionService;

    public AccDefaultSetupDTO findByOrganizationId() {
        return accDefaultSetupRepo.findByOrganizationId(sessionService.getOrganizationId())
                .stream()
                .findFirst()
                .map(accDefaultSetupMapper::entityToDto)
                .orElse(null);
    }

}
