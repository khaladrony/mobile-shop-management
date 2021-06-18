package com.rony.erpsoft.accounts.controller;

import com.rony.erpsoft.accounts.actionService.CommonActionService;
import com.rony.erpsoft.accounts.service.AccountsReportService;
import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.KEY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/accounts/report")
public class AccReportController extends AppProperty {

    @Autowired
    AppUtil appUtil;
    @Autowired
    CommonActionService commonActionService;
    @Autowired
    AccountsReportService accountsReportService;

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView view() {
        appUtil.genToken();
        ModelAndView modelAndView = new ModelAndView("accounts/view");
        modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());

        return modelAndView;
    }

    @RequestMapping(value = "/coa_report", method = RequestMethod.GET)
    public ResponseEntity<Resource> charOfAccountsPreview() {

        ByteArrayResource resource = accountsReportService.previewChartOfAccountsReport();

        String fileName = AppUtil.getStringBetweenTwoCharacters(resource.getDescription());

        return commonActionService.reportHeader(fileName, resource);
    }

    @RequestMapping(value = "/account_ledger/{fromDate}/{toDate}/{chartOfAccountsId}/{chartOfAccountsCodeName}", method = RequestMethod.GET)
    public ResponseEntity<Resource> accountLedgerPreview(@PathVariable("fromDate") String fromDate,
                                                         @PathVariable("toDate") String toDate,
                                                         @PathVariable("chartOfAccountsId") long chartOfAccountsId,
                                                         @PathVariable("chartOfAccountsCodeName") String chartOfAccountsCodeName) {

        ByteArrayResource resource = accountsReportService.accountLedger(fromDate,toDate,chartOfAccountsId, chartOfAccountsCodeName);

        String fileName = AppUtil.getStringBetweenTwoCharacters(resource.getDescription());

        return commonActionService.reportHeader(fileName, resource);
    }



    @RequestMapping(value = "/sub_account_ledger/{fromDate}/{toDate}/{chartOfAccountsId}/{chartOfAccountsCodeName}/{subAccountsId}/{accountsSource}", method = RequestMethod.GET)
    public ResponseEntity<Resource> subAccountLedgerPreview(@PathVariable("fromDate") String fromDate,
                                                         @PathVariable("toDate") String toDate,
                                                         @PathVariable("chartOfAccountsId") long chartOfAccountsId,
                                                         @PathVariable("chartOfAccountsCodeName") String chartOfAccountsCodeName,
                                                         @PathVariable("subAccountsId") long subAccountsId,
                                                         @PathVariable("accountsSource") String accountsSource) {

        ByteArrayResource resource = accountsReportService.subAccountLedger(fromDate,toDate,chartOfAccountsId, chartOfAccountsCodeName, subAccountsId, accountsSource, "");

        String fileName = AppUtil.getStringBetweenTwoCharacters(resource.getDescription());

        return commonActionService.reportHeader(fileName, resource);
    }

    @RequestMapping(value = "/sub_account_type_ledger/{fromDate}/{toDate}/{subAccountsId}/{accountsSource}", method = RequestMethod.GET)
    public ResponseEntity<Resource> subAccountTypeWiseLedgerPreview(@PathVariable("fromDate") String fromDate,
                                                         @PathVariable("toDate") String toDate,
                                                         @PathVariable("subAccountsId") long subAccountsId,
                                                         @PathVariable("accountsSource") String accountsSource) {

        ByteArrayResource resource = accountsReportService.subAccountLedger(fromDate,toDate,0,"",subAccountsId, accountsSource, "");

        String fileName = AppUtil.getStringBetweenTwoCharacters(resource.getDescription());

        return commonActionService.reportHeader(fileName, resource);
    }

    @RequestMapping(value = "/bank_ledger/{fromDate}/{toDate}/{chartOfAccountsId}/{bankAccountId}/{accountsUsage}/{accountsSource}", method = RequestMethod.GET)
    public ResponseEntity<Resource> bankLedgerPreview(@PathVariable("fromDate") String fromDate,
                                                         @PathVariable("toDate") String toDate,
                                                         @PathVariable("chartOfAccountsId") long chartOfAccountsId,
                                                         @PathVariable("bankAccountId") long subAccountsId,
                                                         @PathVariable("accountsUsage") String accountsUsage,
                                                         @PathVariable("accountsSource") String accountsSource) {

        ByteArrayResource resource = accountsReportService.subAccountLedger(fromDate,toDate,chartOfAccountsId,"",subAccountsId, accountsSource, accountsUsage);

        String fileName = AppUtil.getStringBetweenTwoCharacters(resource.getDescription());

        return commonActionService.reportHeader(fileName, resource);
    }

    @RequestMapping(value = "/trial_balance/{asOnDate}", method = RequestMethod.GET)
    public ResponseEntity<Resource> trialBalancePreview(@PathVariable("asOnDate") String asOnDate) {

        ByteArrayResource resource = accountsReportService.getTrialBalance(asOnDate);

        String fileName = AppUtil.getStringBetweenTwoCharacters(resource.getDescription());

        return commonActionService.reportHeader(fileName, resource);
    }
}
