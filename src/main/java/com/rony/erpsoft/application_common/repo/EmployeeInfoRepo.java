package com.rony.erpsoft.application_common.repo;

import com.rony.erpsoft.application_common.model.BankInfo;
import com.rony.erpsoft.application_common.model.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface EmployeeInfoRepo extends JpaRepository<EmployeeInfo, Long> {
    List<EmployeeInfo> findAll();

    EmployeeInfo findById(long id);

    EmployeeInfo save(EmployeeInfo employeeInfo);

    @Query("SELECT max(employeeCode) FROM EmployeeInfo ")
    String findLastEmployeeCode();

    @Query("SELECT id as id,  concat(employeeName,' (',employeeCode,')') as name " +
            "FROM EmployeeInfo WHERE status = 1 order by employeeName")
    List<Map<String, Object>> getEmployeeListForDropDown();
}
