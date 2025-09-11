package com.rony.erpsoft.user_auth.service;

import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.user_auth.dto.BranchDTO;
import com.rony.erpsoft.user_auth.mapper.BranchMapper;
import com.rony.erpsoft.user_auth.repo.BranchRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BranchService {

    private final BranchRepo branchRepo;
    private final BranchMapper branchMapper;

    public List<BranchDTO> findAll(){
        return branchRepo.findAll().stream()
                .map(branchMapper::entityToDto)
                .toList();
    }

    public Page<BranchDTO> findAll(Pageable pageable) {
        return branchRepo.findAllByOrderByIdDesc(pageable)
                .map(branchMapper::entityToDto);
    }

    public BranchDTO getById(Long id) {
        return branchRepo.findById(id)
                .map(branchMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + id));
    }

    public AppResponse createAndUpdate(BranchDTO dto) {
        try {
            BranchDTO branchDTO = branchMapper.entityToDto(branchRepo.save(branchMapper.dtoToEntity(dto)));

            return AppResponse.build(HttpStatus.OK).body(branchDTO);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }
}
