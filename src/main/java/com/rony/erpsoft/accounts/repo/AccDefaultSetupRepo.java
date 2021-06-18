package com.rony.erpsoft.accounts.repo;


import com.rony.erpsoft.accounts.model.AccDefaultSetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccDefaultSetupRepo extends JpaRepository<AccDefaultSetup, Long>  {

    AccDefaultSetup findById(long id);
}
