package com.rony.erpsoft.application_common.repo;

import com.rony.erpsoft.application_common.model.AppCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppCodesRepository extends JpaRepository<AppCodes, Long> {

    List<AppCodes> findAllByXcodeAndActiveIsTrue(String code);

    @Query("SELECT a.xcode FROM AppCodes a WHERE a.xtype = :type AND a.active = true")
    List<String> findXcodeByXtypeAndActiveTrue(@Param("type") String type);
}
