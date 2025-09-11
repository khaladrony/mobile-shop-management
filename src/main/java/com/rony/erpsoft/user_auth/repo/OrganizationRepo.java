package com.rony.erpsoft.user_auth.repo;

import com.rony.erpsoft.user_auth.model.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, Long> {
    Organization findById(long id);

    Page<Organization> findAllByOrderByIdDesc(Pageable pageable);
}
