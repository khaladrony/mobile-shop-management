package com.rony.erpsoft.accounts.service;

import com.rony.erpsoft.accounts.dto.SubCOADropdownDTO;
import com.rony.erpsoft.accounts.model.AccChartOfAccounts;
import com.rony.erpsoft.accounts.model.AccJournalDetails;
import com.rony.erpsoft.accounts.model.AccJournalMaster;
import com.rony.erpsoft.accounts.model.AccLedgerDto;
import com.rony.erpsoft.accounts.model.enums.AccountsSource;
import com.rony.erpsoft.accounts.model.enums.AccountsUsage;
import com.rony.erpsoft.accounts.model.enums.VoucherStatus;
import com.rony.erpsoft.accounts.model.enums.VoucherType;
import com.rony.erpsoft.accounts.repo.AccJournalDetailsRepo;
import com.rony.erpsoft.accounts.repo.AccJournalMasterRepo;
import com.rony.erpsoft.application_common.service.BankInfoService;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.AppUtil;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccJournalMasterService implements IAccJournalMasterService {
    @Autowired
    AccJournalMasterRepo accJournalMasterRepo;
    @Autowired
    SessionService sessionService;
    @Autowired
    AccChartOfAccountsService accChartOfAccountsService;
    @Autowired
    NamedParameterJdbcTemplate nDB;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    AccJournalDetailsRepo accJournalDetailsRepo;
    @Autowired
    AccSubAccountsService accSubAccountsService;
    @Autowired
    BankInfoService bankInfoService;

    @Override
    public List<AccJournalMaster> findAll() {
        return null;
    }

    @Override
    public AccJournalMaster findById(long id) {
        return accJournalMasterRepo.findById(id);
    }

    @Override
    public AccJournalMaster save(AccJournalMaster journalMaster) {
        return accJournalMasterRepo.save(journalMaster);
    }

    public List<Map<String, Object>> getVoucherListByVoucherType(String voucherType) {

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT id as id, voucher_no as voucherNo, voucher_date as voucherDate, ");
        sql.append(" particulars as particulars, amount as amount, payment_type as paymentType, status as status  FROM acc_journal_master ");

        Map<String, Object> params = new HashMap<>();
        if (!voucherType.equals("")) {
            sql.append(" WHERE  voucher_type=:voucher_type");
            params.put("voucher_type", voucherType);
        } else {
            sql.append(" WHERE  1=1 ");
        }
        return nDB.queryForList(sql.toString(), params);
    }

    public Map<String, Object> filter(int currentPage, int itemPerPage, Map<String, Object> request) throws Exception {

        request.put("offset", (currentPage - 1) * itemPerPage);
        request.put("limit", itemPerPage);

        Map<String, Object> data = new HashMap<>();
        data.put("itemCount", count(request));
        data.put("items", rowsData(request));

        return data;
    }

    public List<Map<String, Object>> rowsData(Map<String, Object> params) {

        StringBuilder sql;
        sql = voucherListQueryString(params, false);
        sql.append(" ORDER BY voucher_no desc limit :offset, :limit ");

        return nDB.queryForList(sql.toString(), params);
    }

    public Long count(Map<String, Object> params) {

        StringBuilder sql;
        sql = voucherListQueryString(params, true);

        return nDB.queryForObject(sql.toString(), params, Long.class);
    }

    public StringBuilder voucherListQueryString(Map<String, Object> params, boolean isCount) {

        String from_date = AppUtil.toString(params.get("from_date"));
        String to_date = AppUtil.toString(params.get("to_date"));
        String voucher_no = AppUtil.toString(params.get("voucher_no"));
        String payment_type = AppUtil.toString(params.get("payment_type"));
        String voucher_type = AppUtil.toString(params.get("voucher_type"));
        String status = AppUtil.toString(params.get("status"));

        StringBuilder sql = new StringBuilder();

        if (isCount) {
            sql.append("SELECT count(id) FROM acc_journal_master WHERE 1=1 ");
        } else {
            sql.append("SELECT id, voucher_no, voucher_date , particulars, " +
                    "amount, payment_type, voucher_type, status FROM acc_journal_master WHERE 1=1");
        }


        if (voucher_no != null && !voucher_no.equals("")) {
            sql.append(" AND voucher_no=:voucher_no ");
            params.put("voucher_no", voucher_no);
        }

        if (from_date != null && !from_date.equals("")) {
            if (from_date.length() == 16) from_date += ":00";
            sql.append(" AND voucher_date >= :from_date ");
            params.put("from_date", from_date.concat(" 00:00:00"));
        }

        if (to_date != null && !to_date.equals("")) {
            if (to_date.length() == 16) to_date += ":00";
            sql.append(" AND voucher_date <= :to_date ");
            params.put("to_date", to_date.concat(" 23:59:59"));
        }

        if (payment_type != null && !payment_type.equals("")) {
            sql.append(" AND payment_type = :payment_type ");
            params.put("payment_type", payment_type);
        }

        if (voucher_type != null && !voucher_type.equals("")) {
            sql.append(" AND voucher_type = :voucher_type ");
            params.put("voucher_type", voucher_type);
        }

        if (status != null && !status.equals("")) {
            sql.append(" AND status = :status ");
            params.put("status", status);
        }

        sql.append(" AND created_by = :createdBy ");
        params.put("createdBy", sessionService.getUserId());

        return sql;
    }


    public String voucherNoGeneration(String voucherPrefix, int year, int month) {
        String voucherNo = "";
        String monthPadding = AppUtil.rightPad(String.valueOf(month), 2, '0');
        String prefix = voucherPrefix + String.valueOf(year) + monthPadding;

        voucherNo = accJournalMasterRepo.findLastVoucherNoByVoucherPrefixAndYearAndMonth(voucherPrefix, year, month);
        if (voucherNo == null) {
            voucherNo = prefix + AppUtil.rightPad("1", 4, '0');
        } else {
            String lastFourDigits = voucherNo.substring(voucherNo.length() - 4);
            int incrementVoucherNo = Integer.parseInt(lastFourDigits) + 1;
            String intToStringValue = String.valueOf(incrementVoucherNo);
            voucherNo = prefix + AppUtil.rightPad(intToStringValue, 4, '0');
        }
        return voucherNo;
    }

    public AccJournalMaster journalMasterPropertySet(AccJournalMaster journalMaster) {

        journalMaster.setStatus(VoucherStatus.DRAFT.code());
        journalMaster.setVoucherDate(new Date());
        journalMaster.setYear(AppUtil.getYear(journalMaster.getVoucherDate()));
        journalMaster.setMonth(AppUtil.getMonth(journalMaster.getVoucherDate()));
        journalMaster.setVoucherType(getVoucherType(journalMaster.getVoucherPrefix()));
        journalMaster.setAction(getVoucherAction(journalMaster.getVoucherPrefix()));

        journalMaster.setOrganizationId(sessionService.getOrganizationId());
        journalMaster.setCreatedBy(sessionService.getUserId());
        journalMaster.setCreateOn(new Date());

        return journalMaster;
    }

    public List<AccJournalDetails> getJournalDetails(List<AccJournalDetails> journalDetailList) {

        for (AccJournalDetails journalDetails : journalDetailList) {
            journalDetails.setOrganizationId(sessionService.getOrganizationId());
            setVoucherDetailsProperty(journalDetails);
        }

        return journalDetailList;
    }

    public String getVoucherType(String voucherPrefix) {
        String voucherType = "";
        if (voucherPrefix.equals(VoucherType.DEBIT_VOUCHER.prefix())) {
            voucherType = VoucherType.DEBIT_VOUCHER.code();
        } else if (voucherPrefix.equals(VoucherType.CREDIT_VOUCHER.prefix())) {
            voucherType = VoucherType.CREDIT_VOUCHER.code();
        } else if (voucherPrefix.equals(VoucherType.JOURNAL_VOUCHER.prefix())) {
            voucherType = VoucherType.JOURNAL_VOUCHER.code();
        } else if (voucherPrefix.equals(VoucherType.TRANSFER_VOUCHER.prefix())) {
            voucherType = VoucherType.TRANSFER_VOUCHER.code();
        } else {
            voucherType = VoucherType.OPENING_BALANCE.code();
        }
        return voucherType;
    }

    public String getVoucherAction(String voucherPrefix) {
        String voucherAction = "";
        if (voucherPrefix.equals(VoucherType.DEBIT_VOUCHER.prefix())) {
            voucherAction = "Payment";
        } else if (voucherPrefix.equals(VoucherType.CREDIT_VOUCHER.prefix())) {
            voucherAction = "Receipt";
        } else if (voucherPrefix.equals(VoucherType.JOURNAL_VOUCHER.prefix())) {
            voucherAction = "Journal";
        } else if (voucherPrefix.equals(VoucherType.TRANSFER_VOUCHER.prefix())) {
            voucherAction = "Payment";
        } else {
            voucherAction = "Opening Balance";
        }
        return voucherAction;
    }

    public void setVoucherDetailsProperty(AccJournalDetails journalDetails) {
        AccChartOfAccounts chartOfAccounts = accChartOfAccountsService.findById(journalDetails.getChartOfAccountsId());

        journalDetails.setChartOfAccountsType(chartOfAccounts.getAccountsType());
        journalDetails.setChartOfAccountsSource(chartOfAccounts.getAccountsSource());
        journalDetails.setChartOfAccountsUsage(chartOfAccounts.getAccountsUsage());

        if (journalDetails.getBaseAmount() > 0.0) {
            journalDetails.setDebitCreditFlag("Dr");
        } else {
            journalDetails.setDebitCreditFlag("Cr");
        }

        journalDetails.setCreatedBy(sessionService.getUserId());
        journalDetails.setCreateOn(new Date());
    }

    public List<Map<String, Object>> getVoucherData(String voucherNo) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT '' id, '' voucher_no, '' voucher_date, '' voucher_type, '' payment_type, ");
        sql.append(" '' master_particulars, '' cheque_no, '' cheque_date, ");
        sql.append(" details.chart_of_accounts_code_name coa_name, details.sub_accounts_code_name sub_coa_name, details.prime_amount prime_amount, ");
        sql.append(" (case when details.prime_amount>0 then details.prime_amount else 0 end ) debit_amount, ");
        sql.append(" (case when details.prime_amount<0 then details.prime_amount*-1 else 0 end ) credit_amount, ");
        sql.append(" details.particulars details_particulars, details.row row");
        sql.append(" FROM acc_journal_details details ");
        sql.append(" WHERE journal_master_id=67 ");

        Map<String, Object> params = new HashMap<>();
        params.put("voucherNo", voucherNo);

        return nDB.queryForList(sql.toString(), params);
    }

    public List<Map<String, Object>> findAllByJournalMasterId(long id) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT  ");
        sql.append(" master.id as id,  ");
        sql.append(" master.voucher_no as voucher_no,  ");
        sql.append(" master.voucher_date as voucher_date,  ");
        sql.append(" master.posting_date as posting_date,  ");
        sql.append(" master.voucher_type as voucher_type,  ");
        sql.append(" master.payment_type as payment_type,  ");
        sql.append(" master.particulars as master_particulars, ");
        sql.append(" master.cheque_no as cheque_no, ");
        sql.append(" master.cheque_date as cheque_date, ");
        sql.append(" master.amount as amount, ");
        sql.append(" master.created_by as created_by, ");
        sql.append(" (select concat(first_name ,' ' , last_name) from user_info where user_id=master.created_by) as prepared_by, ");

        sql.append(" details.chart_of_accounts_code_name as coa_name, ");
        sql.append(" details.sub_accounts_code_name as sub_coa_name, ");
        sql.append(" details.prime_amount as prime_amount, ");
        sql.append(" (case when details.prime_amount>0 then details.prime_amount else 0 end ) as debit_amount, ");
        sql.append(" (case when details.prime_amount<0 then details.prime_amount*-1 else 0 end ) as credit_amount, ");
        sql.append(" details.particulars as particulars ");
        sql.append(" FROM acc_journal_master master ");
        sql.append(" inner join acc_journal_details details on master.id = details.journal_master_id and master.id=:id ");
        sql.append(" WHERE master.id=:id ");

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return nDB.queryForList(sql.toString(), params);
    }

    public boolean updateAllVouchersByIds(JSONArray ids) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE acc_journal_master set ");
        sql.append(" status=:status, ");
        sql.append(" posting_date=:posting_date ");
        sql.append(" WHERE id in(:ids) ");

        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids.toList());
        params.put("status", VoucherStatus.POSTED.code());
        params.put("posting_date", new Date());

        return nDB.update(sql.toString(), params) > 0;
    }

    public List<AccLedgerDto> getAccountWiseLedger(String fromDate, String toDate, long chartOfAccountsId, double openingBalance) throws ParseException {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT  ");
        sql.append(" ajm.voucher_no as voucherNo, ");
        sql.append(" DATE_FORMAT(ajm.voucher_date, '%d-%m-%Y') as voucherDate, ");
        sql.append(" ajm.posting_date as postingDate, ");
        sql.append(" ajd.particulars as particulars, ");
        sql.append(" ajd.chart_of_accounts_id as chartOfAccountsId , ");
        sql.append(" (case when ajd.prime_amount>0 then ajd.prime_amount else 0 end ) as debitAmount, ");
        sql.append(" abs(case when ajd.prime_amount<0 then ajd.prime_amount else 0 end ) as creditAmount, ");
        sql.append(" ajd.prime_amount as primeAmount, ");
        sql.append(" 0.00 as balanceAmount ");
        sql.append(" FROM acc_journal_master ajm ");
        sql.append(" inner join acc_journal_details ajd on ajm.id = ajd.journal_master_id ");
        sql.append(" WHERE DATE(ajm.voucher_date) between :fromDate and :toDate ");
        sql.append(" and ajd.chart_of_accounts_id = :chartOfAccountsId ");
        sql.append(" and ajm.status = :status ");
        sql.append(" ORDER BY  ajm.posting_date");

        Map<String, Object> params = new HashMap<>();
        params.put("fromDate", fromDate);
        params.put("toDate", toDate);
        params.put("chartOfAccountsId", chartOfAccountsId);
        params.put("status", VoucherStatus.POSTED.code());

        double balanceAmt = 0.00;
        List<Map<String, Object>> rows = nDB.queryForList(sql.toString(), params);
        List<AccLedgerDto> accLedgerDtoList = new ArrayList<>();
        for (Map row : rows) {
            AccLedgerDto accLedgerDto = new AccLedgerDto();

            accLedgerDto.setVoucherNo((String) row.get("voucherNo"));
            accLedgerDto.setVoucherDate((String) row.get("voucherDate"));
            accLedgerDto.setPostingDate(AppUtil.getLocalDateTimeToDate((LocalDateTime)row.get("postingDate")));
            accLedgerDto.setParticulars((String) row.get("particulars"));
            accLedgerDto.setChartOfAccountsId((Long) row.get("chartOfAccountsId"));
            accLedgerDto.setDebitAmount((double) row.get("debitAmount"));
            accLedgerDto.setCreditAmount((double) row.get("creditAmount"));
            accLedgerDto.setPrimeAmount((double) row.get("primeAmount"));
            balanceAmt = balanceAmt + accLedgerDto.getPrimeAmount() + openingBalance;
            accLedgerDto.setBalanceAmount(balanceAmt);

            accLedgerDtoList.add(accLedgerDto);
            openingBalance = 0.00;
        }

        return accLedgerDtoList;
    }

    public double getOpeningBalance(String date, long chartOfAccountsId, long subAccountsId, String accountsSourceCode) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT  ");
        sql.append(" sum(prime_amount) opening_balance ");
        sql.append(" FROM acc_journal_master ajm ");
        sql.append(" inner join acc_journal_details ajd on ajm.id = ajd.journal_master_id ");
        sql.append(" WHERE ajm.voucher_date < :date ");

        Map<String, Object> params = new HashMap<>();
        params.put("date", date);

        if (chartOfAccountsId > 0) {
            sql.append(" and ajd.chart_of_accounts_id=:chartOfAccountsId ");
            params.put("chartOfAccountsId", chartOfAccountsId);
        }

        if (subAccountsId > 0) {
            sql.append(" and ajd.sub_accounts_id=:subAccountsId ");
            sql.append(" and ajd.chart_of_accounts_source = :accountsSourceCode ");
            params.put("subAccountsId", subAccountsId);
            params.put("accountsSourceCode", accountsSourceCode);
        }

        try {
            return nDB.queryForObject(sql.toString(), params, Double.class);
        } catch (Exception ex) {
            return 0.0;
        }

    }

    public List<AccLedgerDto> getSubAccountWiseLedger(String fromDate, String toDate, long chartOfAccountsId, long subAccountsId, String accountsSource, String accountsUsage) throws ParseException {

        String accountsSourceCode = "";
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT  ");
        sql.append(" ajm.id as id, ");
        sql.append(" ajm.voucher_no as voucherNo, ");
        sql.append(" DATE_FORMAT(ajm.voucher_date, '%d-%m-%Y') as voucherDate, ");
        sql.append(" ajm.posting_date as postingDate, ");
        sql.append(" ajd.particulars as particulars, ");
        sql.append(" ajd.chart_of_accounts_id as chartOfAccountsId, ");
        sql.append(" ajd.sub_accounts_id as subAccountsId, ");
        if (accountsSource.equalsIgnoreCase(AccountsSource.CUSTOMER.code())) {
            accountsSourceCode = AccountsSource.CUSTOMER.code();
            sql.append(" (select concat(customer_name,' (',customer_code,')') from customer_info where id=ajd.sub_accounts_id ) as subAccountsCodeName, ");
        } else if (accountsSource.equalsIgnoreCase(AccountsSource.SUPPLIER.code())) {
            accountsSourceCode = AccountsSource.SUPPLIER.code();
            sql.append(" (select concat(supplier_name,' (',supplier_code,')') from supplier_info where id=ajd.sub_accounts_id ) as subAccountsCodeName, ");
        } else if (accountsSource.equalsIgnoreCase(AccountsSource.EMPLOYEE.code())) {
            accountsSourceCode = AccountsSource.EMPLOYEE.code();
            sql.append(" (select concat(employee_name,' (',employee_code,')') from employee_info where id=ajd.sub_accounts_id ) as subAccountsCodeName, ");
        } else if (accountsSource.equalsIgnoreCase(AccountsSource.SUBACCOUNT.code())) {
            accountsSourceCode = AccountsSource.SUBACCOUNT.code();
            sql.append(" (select concat(sub_accounts_name,' (',sub_accounts_code,')') from acc_sub_accounts where id=ajd.sub_accounts_id and chart_of_accounts_id=ajd.chart_of_accounts_id) as subAccountsCodeName, ");
        } else if (accountsSource.equalsIgnoreCase(AccountsSource.NONE.code()) && accountsUsage.equalsIgnoreCase(AccountsUsage.BANK.code())) {
            accountsSourceCode = AccountsSource.NONE.code();
            sql.append(" (select concat(bank_account_name,' (',bank_account_no,')') from bank_info where id=ajd.sub_accounts_id ) as subAccountsCodeName, ");
        } else {
            sql.append(" '' as subAccountsCodeName, ");
        }
        sql.append(" ajd.chart_of_accounts_type as chartOfAccountsType, ");
        sql.append(" ajd.chart_of_accounts_usage as chartOfAccountsUsage, ");
        sql.append(" ajd.chart_of_accounts_source as chartOfAccountsSource, ");
        sql.append(" (case when ajd.prime_amount>0 then ajd.prime_amount else 0 end ) as debitAmount, ");
        sql.append(" abs(case when ajd.prime_amount<0 then ajd.prime_amount else 0 end ) as creditAmount, ");
        sql.append(" ajd.prime_amount as primeAmount, ");
        sql.append(" 0.00 as balanceAmount ");
        sql.append(" FROM acc_journal_master ajm ");
        sql.append(" inner join acc_journal_details ajd on ajm.id = ajd.journal_master_id ");
        sql.append(" WHERE ajm.status = :status ");
        sql.append(" and DATE(ajm.posting_date) between :fromDate and :toDate ");
        sql.append(" and ajd.chart_of_accounts_source = :accountsSourceCode ");

        Map<String, Object> params = new HashMap<>();
        params.put("fromDate", fromDate);
        params.put("toDate", toDate);
        params.put("status", VoucherStatus.POSTED.code());
        params.put("accountsSourceCode", accountsSourceCode);

        if (chartOfAccountsId > 0) {
            params.put("chartOfAccountsId", chartOfAccountsId);
            sql.append(" and ajd.chart_of_accounts_id = :chartOfAccountsId ");
        }

        if (subAccountsId > 0) {
            sql.append(" and ajd.sub_accounts_id=:subAccountsId ");
            params.put("subAccountsId", subAccountsId);
        } else if (subAccountsId == 0) {
            sql.append(" and ajd.sub_accounts_id > 0 ");
        }

        sql.append(" ORDER BY  ajm.posting_date");

        List<AccLedgerDto> accLedgerDtoList = nDB.query(sql.toString(),params, new BeanPropertyRowMapper(AccLedgerDto.class));

        setOpeningBalance(accLedgerDtoList, fromDate, chartOfAccountsId, subAccountsId, accountsSource, accountsUsage, accountsSourceCode);

        accLedgerDtoList.sort(Comparator.comparing(AccLedgerDto::getSubAccountsId).thenComparing(AccLedgerDto::getId));

        ledgerCumulativeBalanceUpdate(accLedgerDtoList);

        return accLedgerDtoList;
    }

    public void setOpeningBalance(List<AccLedgerDto> accLedgerDtoList, String fromDate, long chartOfAccountsId, long subAccountsId, String accountsSource, String accountsUsage, String accountsSourceCode){
        List<Long> subAccountIds = new ArrayList<>();

        if (subAccountsId == 0) {
            subAccountIds = getSubAccountIds(chartOfAccountsId, accountsSource, accountsUsage);
        } else {
            subAccountIds.add(subAccountsId);
        }

        for (Long subAccountId : subAccountIds) {
            AccLedgerDto accLedgerDto = new AccLedgerDto();
            double openingBalance = getOpeningBalance(fromDate, chartOfAccountsId, subAccountId, accountsSourceCode);

            AccLedgerDto ledgerDto = accLedgerDtoList.stream()
                    .filter(obj -> subAccountId.equals(obj.getSubAccountsId()))
                    .findAny()
                    .orElse(null);
            if (openingBalance == 0.0 && ledgerDto == null) {
                continue;
            }
            accLedgerDto.setId(0);
            accLedgerDto.setVoucherNo("Opening Balance :");
            accLedgerDto.setSubAccountsId(subAccountId);
            if(ledgerDto == null){
                accLedgerDto.setSubAccountsCodeName(accSubAccountsService.getSubAccountNameCode(accountsSourceCode, chartOfAccountsId, subAccountId));
            } else {
                accLedgerDto.setSubAccountsCodeName(ledgerDto.getSubAccountsCodeName());
            }
            accLedgerDto.setPostingDate(AppUtil.toDate(fromDate));
            accLedgerDto.setPrimeAmount(0);
            accLedgerDto.setBalanceAmount(openingBalance);

            accLedgerDtoList.add(accLedgerDto);
        }
    }

    public void ledgerCumulativeBalanceUpdate(List<AccLedgerDto> accLedgerDtoList){
        double cumulativeAmt = 0.0;
        double openingAmt = 0.0;
        for (AccLedgerDto accLedgerDto : accLedgerDtoList) {
            if (accLedgerDto.getId() == 0) {
                cumulativeAmt = 0.0;
                openingAmt = accLedgerDto.getBalanceAmount();
            } else {
                cumulativeAmt = cumulativeAmt + openingAmt + accLedgerDto.getPrimeAmount();
                accLedgerDto.setBalanceAmount(cumulativeAmt);
                openingAmt = 0.0;
            }

        }
    }

    public List<Long> getSubAccountIds(long chartOfAccountsId, String accountsSource, String accountsUsage) {
        List<SubCOADropdownDTO> subCOAs = new ArrayList<>();
        if (accountsSource.equalsIgnoreCase(AccountsSource.NONE.code())) {
            if(accountsUsage.equalsIgnoreCase(AccountsUsage.BANK.code())){
                subCOAs = bankInfoService.getBankAccountListForDropDown();
            }
        } else {
            subCOAs = accSubAccountsService.findAllSubAccountListByAccountsSource(accountsSource, chartOfAccountsId);
        }
        return subCOAs.stream()
                .map(SubCOADropdownDTO::getId)
                .collect(Collectors.toList());
    }

    public List<AccLedgerDto> getTrialBalance(String asOnDate){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT  ");
        sql.append(" ajd.chart_of_accounts_id as chartOfAccountsId, ");
        sql.append(" coa.accounts_code as accountsCode, ");
        sql.append(" coa.accounts_name as accountsName, ");
        sql.append(" sum((case when ajd.prime_amount > 0 then ajd.prime_amount else 0 end)) as debitAmount, ");
        sql.append(" sum(abs(case when ajd.prime_amount < 0 then ajd.prime_amount else 0 end)) as creditAmount, ");
        sql.append(" sum(ajd.prime_amount) as balanceAmount ");
        sql.append(" FROM acc_journal_master ajm ");
        sql.append(" INNER JOIN acc_journal_details ajd on ajm.id = ajd.journal_master_id ");
        sql.append(" INNER JOIN acc_chart_of_accounts coa on ajd.chart_of_accounts_id=coa.id ");
        sql.append(" WHERE ajm.status = 'POSTED' ");
        sql.append(" and DATE(ajm.posting_date) <= :asOnDate ");



        Map<String, Object> params = new HashMap<>();
        params.put("asOnDate", asOnDate);

        sql.append(" GROUP BY ajd.chart_of_accounts_id ");
        sql.append(" ORDER BY coa.accounts_code,coa.accounts_name");

        return nDB.query(sql.toString(), params, new BeanPropertyRowMapper(AccLedgerDto.class));

    }
}
