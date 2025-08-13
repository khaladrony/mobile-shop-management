package com.rony.erpsoft.application_common.repo;

import com.rony.erpsoft.application_common.model.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeInfoRepo extends JpaRepository<EmployeeInfo, Long> {

    EmployeeInfo findById(long id);

    @Query("SELECT max(employeeCode) FROM EmployeeInfo ")
    String findLastEmployeeCode();

    List<EmployeeInfo> findAllByStatusTrueOrderByEmployeeName();
}
