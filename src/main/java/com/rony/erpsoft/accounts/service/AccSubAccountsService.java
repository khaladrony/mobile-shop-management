package com.rony.erpsoft.accounts.service;

import com.rony.erpsoft.accounts.dto.SubCOADropdownDTO;
import com.rony.erpsoft.accounts.model.AccChartOfAccounts;
import com.rony.erpsoft.accounts.model.AccSubAccounts;
import com.rony.erpsoft.accounts.model.enums.AccountsSource;
import com.rony.erpsoft.accounts.repo.AccChartOfAccountsRepo;
import com.rony.erpsoft.accounts.repo.AccSubAccountsRepo;
import com.rony.erpsoft.application_common.model.BankInfo;
import com.rony.erpsoft.application_common.model.CustomerInfo;
import com.rony.erpsoft.application_common.model.EmployeeInfo;
import com.rony.erpsoft.application_common.model.SupplierInfo;
import com.rony.erpsoft.application_common.service.BankInfoService;
import com.rony.erpsoft.application_common.service.CustomerInfoService;
import com.rony.erpsoft.application_common.service.EmployeeInfoService;
import com.rony.erpsoft.application_common.service.SupplierInfoService;
import com.rony.erpsoft.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AccSubAccountsService implements IAccSubAccountsService {
    @Autowired
    AccSubAccountsRepo accSubAccountsRepo;
    @Autowired
    AccChartOfAccountsService accChartOfAccountsService;
    @Autowired
    CustomerInfoService customerInfoService;
    @Autowired
    SupplierInfoService supplierInfoService;
    @Autowired
    EmployeeInfoService employeeInfoService;
    @Autowired
    BankInfoService bankInfoService;


    @Override
    public List<AccSubAccounts> findAll() {
        return accSubAccountsRepo.findAll();
    }

    @Override
    public List<AccSubAccounts> findAllByCoa(Long coaId) {
        return accSubAccountsRepo.findAllByCoa(coaId);
    }

    @Override
    public AccSubAccounts findById(long id) {
        return accSubAccountsRepo.findById(id);
    }

    @Override
    public String findLastSubAccountsCodeByCoaId(Long coaId) {
        return accSubAccountsRepo.findLastSubAccountsCodeByCoaId(coaId);
    }

    @Override
    public AccSubAccounts save(AccSubAccounts subAccounts) {
        return accSubAccountsRepo.save(subAccounts);
    }

    @Override
    public List<SubCOADropdownDTO> findAllSubAccountListByAccountsSource(String accountsSource, Long chartOfAccountsId) {
        if(accountsSource.equalsIgnoreCase(AccountsSource.CUSTOMER.code())){
            return customerInfoService.getCustomerListForDropDown();
        } else if(accountsSource.equalsIgnoreCase(AccountsSource.SUPPLIER.code())){
            return supplierInfoService.getSupplierListForDropDown();
        } else if(accountsSource.equalsIgnoreCase(AccountsSource.EMPLOYEE.code())){
            return employeeInfoService.getEmployeeListForDropDown();
        } else {
            return accSubAccountsRepo.findAllSubCOAListForDropDown(chartOfAccountsId);
        }
    }

    public String getSubAccountNameCode(String accountsSource, Long chartOfAccountsId, Long subAccountsId) {
        if (accountsSource.equalsIgnoreCase(AccountsSource.CUSTOMER.code())) {
            CustomerInfo customerInfo = customerInfoService.findById(subAccountsId);
            return customerInfo.getCustomerName() + " (" + customerInfo.getCustomerCode() + ")";

        } else if (accountsSource.equalsIgnoreCase(AccountsSource.SUPPLIER.code())) {
            SupplierInfo supplierInfo = supplierInfoService.findById(subAccountsId);
            return supplierInfo.getSupplierName() + " (" + supplierInfo.getSupplierCode() + ")";

        } else if (accountsSource.equalsIgnoreCase(AccountsSource.EMPLOYEE.code())) {
            EmployeeInfo employeeInfo = employeeInfoService.findById(subAccountsId);
            return employeeInfo.getEmployeeName() + " (" + employeeInfo.getEmployeeCode() + ")";

        } else if (accountsSource.equalsIgnoreCase(AccountsSource.NONE.code())) {
            BankInfo bankInfo = bankInfoService.findById(subAccountsId);
            return bankInfo.getBankAccountName() + " (" + bankInfo.getBankAccountNo() + ")";

        } else {
            AccSubAccounts accSubAccounts = accSubAccountsRepo.findByIdAndChartOfAccountsId(subAccountsId, chartOfAccountsId);
            return accSubAccounts.getSubAccountsName() + " (" + accSubAccounts.getSubAccountsCode() + ")";
        }
    }

    public String subAccountsCodeGenerate(Long chartOfAccountsId, String chartOfAccountsCode) {
        String subAccountsCode = "";
        int accountsCodeInt = 0;

        String availableSubAccountCode = findLastSubAccountsCodeByCoaId(chartOfAccountsId);
        if (availableSubAccountCode == null) {
            subAccountsCode = chartOfAccountsCode + "01";
        } else {
            accountsCodeInt = Integer.parseInt(availableSubAccountCode) + 1;
            subAccountsCode = String.valueOf(accountsCodeInt);
        }

        return subAccountsCode;
    }

}
