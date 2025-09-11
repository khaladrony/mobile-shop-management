package com.rony.erpsoft.user_auth.service;

import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.user_auth.dto.OrganizationDTO;
import com.rony.erpsoft.user_auth.mapper.OrganizationMapper;
import com.rony.erpsoft.user_auth.repo.OrganizationRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrganizationService {

    private final OrganizationRepo repository;
    private final OrganizationMapper mapper;

    public List<OrganizationDTO> findAll(){
        return repository.findAll().stream()
                .map(mapper::entityToDto)
                .toList();
    }

    public Page<OrganizationDTO> findAll(Pageable pageable) {
        return repository.findAllByOrderByIdDesc(pageable)
                .map(mapper::entityToDto);
    }

    public OrganizationDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found with id: " + id));
    }

    public OrganizationDTO getOrganization() {
        return repository.findAll()
                .stream().findFirst()
                .map(mapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found"));
    }

    public AppResponse createAndUpdate(OrganizationDTO dto) {
        try {
            OrganizationDTO organizationDTO = mapper.entityToDto(repository.save(mapper.dtoToEntity(dto)));

            return AppResponse.build(HttpStatus.OK).body(organizationDTO);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }
}
