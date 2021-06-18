var JMODULE_NAME = "accounts";

var JCONTROLLER = {
    ACC_DEBIT_VOUCHER: "debit_voucher",
    ACC_CREDIT_VOUCHER: "credit_voucher",
    ACC_JOURNAL_VOUCHER: "journal_voucher",
    ACC_TRANSFER_VOUCHER: "transfer_voucher",
    ACC_CHART_OF_ACCOUNTS: "chart_of_accounts",
    ACC_SUB_ACCOUNTS: "sub_accounts",
    ACC_VOUCHER_POST: "voucher_post",
    ACC_VOUCHER_REPORT: "report"
};
var JCOMPONENT = {
    acc_debit_voucher_add_view: "acc_debit_voucher_add_view",
    acc_debit_voucher_update_view: "acc_debit_voucher_update_view",
    acc_debit_voucher_list_view: "acc_debit_voucher_list_view",

    acc_credit_voucher_add_view: "acc_credit_voucher_add_view",
    acc_credit_voucher_update_view: "acc_credit_voucher_update_view",
    acc_credit_voucher_list_view: "acc_credit_voucher_list_view",

    acc_journal_voucher_add_view: "acc_journal_voucher_add_view",
    acc_journal_voucher_update_view: "acc_journal_voucher_update_view",
    acc_journal_voucher_list_view: "acc_journal_voucher_list_view",

    acc_transfer_voucher_add_view: "acc_transfer_voucher_add_view",
    acc_transfer_voucher_update_view: "acc_transfer_voucher_update_view",
    acc_transfer_voucher_list_view: "acc_transfer_voucher_list_view",

    acc_chart_of_accounts_add_view: "acc_chart_of_accounts_add_view",
    acc_chart_of_accounts_update_view: "acc_chart_of_accounts_update_view",
    acc_chart_of_accounts_list_view: "acc_chart_of_accounts_list_view",

    acc_sub_accounts_add_view: "acc_sub_accounts_add_view",
    acc_sub_accounts_update_view: "acc_sub_accounts_update_view",

    acc_voucher_posting_list_view: "acc_voucher_posting_list_view",

    acc_report_account_wise_form_view: "acc_report_account_wise_form_view",
    acc_report_sub_account_wise_form_view: "acc_report_sub_account_wise_form_view",
    acc_report_cus_sup_emp_wise_form_view: "acc_report_cus_sup_emp_wise_form_view",
    acc_report_bank_form_view: "acc_report_bank_form_view",
    acc_trial_balance_form_view: "acc_trial_balance_form_view"


};

var API = {
    ACC_DEBIT_VOUCHER_SAVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_DEBIT_VOUCHER + "/save",
    ACC_DEBIT_VOUCHER_UPDATE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_DEBIT_VOUCHER + "/update",
    ACC_DEBIT_VOUCHER_GET: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_DEBIT_VOUCHER + "/get",
    ACC_DEBIT_VOUCHER_GET_ALL: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_DEBIT_VOUCHER + "/get/all",
    ACC_DEBIT_VOUCHER_LIST: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_DEBIT_VOUCHER + "/get/voucher_list",
    ACC_DEBIT_VOUCHER_FILTER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_DEBIT_VOUCHER + "/filter",

    ACC_CREDIT_VOUCHER_SAVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_CREDIT_VOUCHER + "/save",
    ACC_CREDIT_VOUCHER_UPDATE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_CREDIT_VOUCHER + "/update",
    ACC_CREDIT_VOUCHER_GET: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_CREDIT_VOUCHER + "/get",
    ACC_CREDIT_VOUCHER_GET_ALL: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_CREDIT_VOUCHER + "/get/all",
    ACC_CREDIT_VOUCHER_LIST: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_CREDIT_VOUCHER + "/get/voucher_list",
    ACC_CREDIT_VOUCHER_FILTER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_CREDIT_VOUCHER + "/filter",

    ACC_JOURNAL_VOUCHER_SAVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_JOURNAL_VOUCHER + "/save",
    ACC_JOURNAL_VOUCHER_UPDATE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_JOURNAL_VOUCHER + "/update",
    ACC_JOURNAL_VOUCHER_GET: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_JOURNAL_VOUCHER + "/get",
    ACC_JOURNAL_VOUCHER_GET_ALL: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_JOURNAL_VOUCHER + "/get/all",
    ACC_JOURNAL_VOUCHER_LIST: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_JOURNAL_VOUCHER + "/get/voucher_list",
    ACC_JOURNAL_VOUCHER_FILTER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_JOURNAL_VOUCHER + "/filter",
    ACC_VOUCHER_REPORT_VIEW: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_JOURNAL_VOUCHER + "/report",

    ACC_TRANSFER_VOUCHER_SAVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_TRANSFER_VOUCHER + "/save",
    ACC_TRANSFER_VOUCHER_UPDATE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_TRANSFER_VOUCHER + "/update",
    ACC_TRANSFER_VOUCHER_GET: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_TRANSFER_VOUCHER + "/get",
    ACC_TRANSFER_VOUCHER_GET_ALL: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_TRANSFER_VOUCHER + "/get/all",
    ACC_TRANSFER_VOUCHER_LIST: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_TRANSFER_VOUCHER + "/get/voucher_list",
    ACC_TRANSFER_VOUCHER_FILTER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_TRANSFER_VOUCHER + "/filter",

    ACC_CHART_OF_ACCOUNTS_SAVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_CHART_OF_ACCOUNTS + "/save",
    ACC_CHART_OF_ACCOUNTS_UPDATE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_CHART_OF_ACCOUNTS + "/update",
    ACC_CHART_OF_ACCOUNTS_GET_ALL: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_CHART_OF_ACCOUNTS + "/get/all",
    ACC_CHART_OF_ACCOUNTS_GET: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_CHART_OF_ACCOUNTS + "/get",
    ACC_CHART_OF_ACCOUNTS_LIST_DROP_DOWN: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_CHART_OF_ACCOUNTS + "/get/coa_list",
    ACC_CHART_OF_ACCOUNTS_BY_USAGES_TYPE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_CHART_OF_ACCOUNTS + "/get/coa_by_usage",
    ACC_VOUCHER_STATUS_LIST: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_CHART_OF_ACCOUNTS + "/get/voucher_status_list",
    ACC_CHART_OF_ACCOUNTS_FILTER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_CHART_OF_ACCOUNTS + "/filter",
    ACC_CHART_OF_ACCOUNTS_VOUCHER_COUNT: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_CHART_OF_ACCOUNTS + "/count_voucher",

    ACC_SUB_ACCOUNTS_SAVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_SUB_ACCOUNTS + "/save",
    ACC_SUB_ACCOUNTS_UPDATE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_SUB_ACCOUNTS + "/update",
    ACC_SUB_ACCOUNTS_GET_ALL: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_SUB_ACCOUNTS + "/get/all",
    ACC_SUB_ACCOUNTS_GET_ALL_BY_COA: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_SUB_ACCOUNTS + "/get_all/coa",
    ACC_SUB_ACCOUNTS_GET: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_SUB_ACCOUNTS + "/get",
    ACC_GET_SUB_ACCOUNTS_CODE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_SUB_ACCOUNTS + "/get/sub_account_code",
    ACC_GET_SUB_ACCOUNTS_LIST: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_SUB_ACCOUNTS + "/get/sub_account_list",

    BANK_ACCOUNT_LIST: _baseurl_ + "application_common/bank_info/get/list",
    ACC_VOUCHER_POSTING: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_VOUCHER_POST + "/posting",
    ACC_VOUCHER_POSTING_FILTER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_VOUCHER_POST + "/filter",

    ACC_REPORT_CHART_OF_ACCOUNTS_VIEW: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_VOUCHER_REPORT + "/coa_report",
    ACC_REPORT_ACCOUNT_WISE_LEDGER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_VOUCHER_REPORT + "/account_ledger",
    ACC_REPORT_SUB_ACCOUNT_WISE_LEDGER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_VOUCHER_REPORT + "/sub_account_ledger",
    ACC_REPORT_CUS_SUP_EMP_WISE_LEDGER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_VOUCHER_REPORT + "/sub_account_type_ledger",
    ACC_REPORT_BANK_LEDGER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_VOUCHER_REPORT + "/bank_ledger",
    ACC_REPORT_TRIAL_BALANCE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ACC_VOUCHER_REPORT + "/trial_balance"


};

var VOUCHER_KEY = {
    PAYMENT_TYPE: {
        CASH: "Cash",
        BANK: "Bank",
        JOURNAL: "Journal"
    },
    CHEQUE_STATUS: {
        NOT_CLEARED: "NOT_CLEARED"
    },
    TYPE: {
        DEBIT_VOUCHER: "DEBIT_VOUCHER",
        CREDIT_VOUCHER: "CREDIT_VOUCHER",
        JOURNAL_VOUCHER: "JOURNAL_VOUCHER",
        TRANSFER_VOUCHER: "TRANSFER_VOUCHER",
        OPENING_BALANCE: "OPENING_BALANCE"
    },
    PREFIX: {
        DEBIT_VOUCHER: "DV",
        CREDIT_VOUCHER: "CV",
        JOURNAL_VOUCHER: "JV",
        TRANSFER_VOUCHER: "TV",
        OPENING_BALANCE: "OB"
    },
    STATUS: {
        DRAFT: "DRAFT"
    },
    ACCOUNTS_USAGE:{
        LEDGER: "LEDGER",
        CASH: "CASH",
        BANK: "BANK"
    },
    ACCOUNTS_SOURCE:{
        NONE: "NONE",
        SUBACCOUNT: "SUBACCOUNT",
        CUSTOMER: "CUSTOMER",
        SUPPLIER: "SUPPLIER",
        EMPLOYEE: "EMPLOYEE"
    }

};