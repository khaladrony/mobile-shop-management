package com.rony.erpsoft.application_common.repo;

import com.rony.erpsoft.application_common.dto.DropdownAppCodesDTO;
import com.rony.erpsoft.application_common.model.AppCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface AppCodesRepository extends JpaRepository<AppCodes, Long> {

    List<AppCodes> findAllByXcodeAndActiveIsTrue(String code);

    @Query("SELECT new com.rony.erpsoft.application_common.dto.DropdownAppCodesDTO(a.xtype, a.xcode, a.description, a.active) FROM AppCodes a WHERE a.xtype = :type AND a.active = true")
    List<DropdownAppCodesDTO> findXcodeByXtypeAndActiveTrue(@Param("type") String type);
}
