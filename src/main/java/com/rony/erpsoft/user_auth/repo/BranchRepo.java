package com.rony.erpsoft.user_auth.repo;

import com.rony.erpsoft.user_auth.model.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepo extends JpaRepository<Branch, Long> {

    Branch findByCode(String code);

    Page<Branch> findAllByOrderByIdDesc(Pageable pageable);
}
