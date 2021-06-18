package com.rony.erpsoft.accounts.service;

import com.rony.erpsoft.accounts.model.AccChartOfAccounts;
import com.rony.erpsoft.accounts.model.AccDefaultSetup;
import com.rony.erpsoft.accounts.model.AccJournalDetails;
import com.rony.erpsoft.accounts.model.AccLedgerDto;
import com.rony.erpsoft.accounts.model.enums.AccountsSource;
import com.rony.erpsoft.accounts.model.enums.AccountsUsage;
import com.rony.erpsoft.accounts.model.enums.PaymentType;
import com.rony.erpsoft.accounts.model.enums.VoucherType;
import com.rony.erpsoft.accounts.repo.AccDefaultSetupRepo;
import com.rony.erpsoft.user_auth.model.Organization;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.NumberToBanglaTaka;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountsReportService {

    Logger logger = LoggerFactory.getLogger(AccountsReportService.class);

    @Autowired
    AccChartOfAccountsService accChartOfAccountsService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    AccJournalMasterService accJournalMasterService;
    @Autowired
    NumberToBanglaTaka numberToBanglaTaka;
    @Autowired
    SessionService sessionService;
    @Autowired
    AccDefaultSetupRepo accDefaultSetupRepo;

    public static final String ACCOUNT_REPORTS_DIR = "/view/accounts/report/jrxml/";

    public ByteArrayResource voucherPreview(long id) {

        try {

            String fileName = "voucherViewA5";
            AccDefaultSetup accDefaultSetup = accDefaultSetupRepo.findById((long) 1);
            if (accDefaultSetup != null && accDefaultSetup.getVoucherPrintView().equalsIgnoreCase("A4")) {
                fileName = "voucherView";
            } else if (accDefaultSetup != null && accDefaultSetup.getVoucherPrintView().equalsIgnoreCase("A5")) {
                fileName = "voucherViewA5";
            }

            Organization organization = sessionService.getOrganization();
            List<Map<String, Object>> voucherData = accJournalMasterService.findAllByJournalMasterId(id);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(voucherData);

            Map<String, Object> voucherMasterData = voucherData.get(0);
            Map<String, Object> parameters = new HashMap<>();

            parameters.put("organizationName", organization.getName());
            parameters.put("orgAddress", organization.getAddress1());

            String voucher_type = VoucherType.valueOf(voucherMasterData.get("voucher_type").toString()).displayName();
            parameters.put("voucher_type", voucher_type);

            parameters.put("payment_type", voucherMasterData.get("payment_type"));
            parameters.put("voucher_no", voucherMasterData.get("voucher_no"));
            parameters.put("prepared_by", voucherMasterData.get("prepared_by"));

            Date voucherDate = AppUtil.getLocalDateTimeToDate((LocalDateTime) voucherMasterData.get("voucher_date"));
            parameters.put("voucher_date", AppUtil.getDateFormatAsString(voucherDate));

            if (voucherMasterData.get("posting_date") == null) {
                parameters.put("posting_date", null);
            } else {
                Date postingDate = AppUtil.getLocalDateTimeToDate((LocalDateTime) voucherMasterData.get("posting_date"));
                parameters.put("posting_date", AppUtil.getDateFormatAsString(postingDate));
            }

            double voucherAmount = (Double) voucherMasterData.get("amount");
            parameters.put("amount_in_words", numberToBanglaTaka.convert(voucherAmount));

            if (AppUtil.toString(voucherMasterData.get("payment_type")).equalsIgnoreCase(PaymentType.BANK.toString())) {
                parameters.put("bank_account", voucherMasterData.get("sub_coa_name"));
                parameters.put("cheque_no", voucherMasterData.get("cheque_no"));
                Date outputDate = AppUtil.getLocalDateTimeToDate((LocalDateTime) voucherMasterData.get("cheque_date"));
                parameters.put("cheque_date", AppUtil.getDateFormatAsString(outputDate));
            }

            return exportReportToPdf(dataSource, fileName, parameters);

        } catch (Exception e) {
            logger.info("Voucher preview report: " + e.getMessage());
            return null;
        }
    }

    public ByteArrayResource accountLedger(String fromDate, String toDate, long chartOfAccountsId, String chartOfAccountsCodeName) {
        try {

            String fileName = "accountLedger";
            Organization organization = sessionService.getOrganization();
            double openingBalance = accJournalMasterService.getOpeningBalance(fromDate, chartOfAccountsId, 0, "");
            List<AccLedgerDto> accountWiseLedgerData = accJournalMasterService.getAccountWiseLedger(fromDate, toDate, chartOfAccountsId, openingBalance);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(accountWiseLedgerData);


            Map<String, Object> parameters = new HashMap<>();
            parameters.put("chartOfAccountsId", chartOfAccountsId);
            parameters.put("fromDate", AppUtil.getDateStringByPattern(AppUtil.toDate(fromDate), "dd MMMM yyyy"));
            parameters.put("toDate", AppUtil.getDateStringByPattern(AppUtil.toDate(toDate), "dd MMMM yyyy"));
            parameters.put("openingBalance", openingBalance);
            parameters.put("accheadcodeparam", chartOfAccountsCodeName);
            parameters.put("organizationName", organization.getName());
            parameters.put("orgAddress", organization.getAddress1());

            return exportReportToPdf(dataSource, fileName, parameters);

        } catch (Exception e) {
            logger.info("Voucher preview report: " + e.getMessage());
            return null;
        }
    }

    public ByteArrayResource subAccountLedger(String fromDate, String toDate, long chartOfAccountsId, String chartOfAccountsCodeName, long subAccountsId, String accountsSource, String accountsUsage) {
        try {

            String fileName = "subAccountLedger";
            Organization organization = sessionService.getOrganization();
            List<AccLedgerDto> accountWiseLedgerData = accJournalMasterService.getSubAccountWiseLedger(fromDate, toDate, chartOfAccountsId, subAccountsId, accountsSource, accountsUsage);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(accountWiseLedgerData);


            Map<String, Object> parameters = new HashMap<>();
            parameters.put("chartOfAccountsId", chartOfAccountsId);
            parameters.put("fromDate", AppUtil.toDate(fromDate));
            parameters.put("toDate", AppUtil.toDate(toDate));
            parameters.put("accheadcodeparam", chartOfAccountsCodeName);
            parameters.put("organizationName", organization.getName());
            parameters.put("orgAddress", organization.getAddress1());

            String reportName = "Sub Account Ledger Report";
            String groupTitle = "Sub Account: ";
            if (accountsSource.equalsIgnoreCase(AccountsSource.CUSTOMER.code())) {
                reportName = "Customer Ledger Report";
                groupTitle = "Customer: ";
            } else if (accountsSource.equalsIgnoreCase(AccountsSource.SUPPLIER.code())) {
                reportName = "Supplier Ledger Report";
                groupTitle = "Supplier: ";
            } else if (accountsSource.equalsIgnoreCase(AccountsSource.EMPLOYEE.code())) {
                reportName = "Employee Ledger Report";
                groupTitle = "Employee: ";
            } else if (accountsSource.equalsIgnoreCase(AccountsSource.NONE.code()) && accountsUsage.equalsIgnoreCase(AccountsUsage.BANK.code())) {
                reportName = "Bank Ledger Report";
                groupTitle = "Bank A/C: ";
            }
            parameters.put("reportName", reportName);
            parameters.put("groupTitle", groupTitle);

            return exportReportToPdf(dataSource, fileName, parameters);

        } catch (Exception e) {
            logger.info("Voucher preview report: " + e.getMessage());
            return null;
        }
    }

    public ByteArrayResource getTrialBalance(String asOnDate) {

        try {
            String fileName = "trial_balance";
            Organization organization = sessionService.getOrganization();
            List<AccLedgerDto> trialBalanceData = accJournalMasterService.getTrialBalance(asOnDate);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(trialBalanceData);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("asOnDate", AppUtil.toDate(asOnDate));
            parameters.put("organizationName", organization.getName());
            parameters.put("orgAddress", organization.getAddress1());
            parameters.put("reportName", "Trial Balance Report");

            return exportReportToPdf(dataSource, fileName, parameters);

        } catch (Exception e) {
            logger.info("Trial balance report: " + e.getMessage());
            return null;
        }
    }

    public ByteArrayResource previewChartOfAccountsReport() {
        String fileName = "chartOfAccounts";

        try {
            Organization organization = sessionService.getOrganization();

            List<AccChartOfAccounts> chartOfAccountsList = accChartOfAccountsService.findAll();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(chartOfAccountsList);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("organizationName", organization.getName());
            parameters.put("orgAddress", organization.getAddress1());

            return exportReportToPdf(dataSource, fileName, parameters);

        } catch (Exception e) {
            logger.info("Chart of accounts report: " + e.getMessage());
            return null;
        }
    }


    private ByteArrayResource exportReportToPdf(JRBeanCollectionDataSource dataSource, String fileName, Map<String, Object> parameters) {

        try {
            URL resourceUrl = request.getSession().getServletContext().getResource(ACCOUNT_REPORTS_DIR + fileName + ".jrxml");
            File file = new File(resourceUrl.toURI());

            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            byte[] reportContent = outputStream.toByteArray();

            return new ByteArrayResource(reportContent, fileName);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            logger.info("REPORT: " + ex.getMessage());
            return null;
        }

    }
}
