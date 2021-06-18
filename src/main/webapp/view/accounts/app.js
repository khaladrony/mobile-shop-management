
var app = angular.module('AccountsManagementApp', ['rt.select2', 'ngPatternRestrict', '720kb.datepicker', 'ngDialog', 'ngToast', 'ui.router','ui.bootstrap', 'ngMaterial', 'ngMessages', 'ngSanitize', 'ngAnimate', 'angular-growl'])

    .config(function($stateProvider, $urlRouterProvider, ngToastProvider, growlProvider) {
        growlProvider.globalTimeToLive(5000);

        ngToastProvider.configure({
            animation: 'slide'
        });

        $stateProvider

        .state(JCOMPONENT.acc_debit_voucher_add_view, {
            url: '/acc_debit_voucher_add_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/debit_voucher/debit_voucher_form.html',
            controller: 'AccDebitVoucherFormCtrl'
        }).state(JCOMPONENT.acc_debit_voucher_update_view, {
            url: '/acc_debit_voucher_update_view/:id',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/debit_voucher/debit_voucher_form.html',
            controller: 'AccDebitVoucherFormCtrl'
        }).state(JCOMPONENT.acc_debit_voucher_list_view, {
            url: '/acc_debit_voucher_list_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/debit_voucher/debit_voucher_list.html',
            controller: 'AccDebitVoucherListCtrl'
        })

        .state(JCOMPONENT.acc_credit_voucher_add_view, {
            url: '/acc_credit_voucher_add_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/credit_voucher/credit_voucher_form.html',
            controller: 'AccCreditVoucherFormCtrl'
        }).state(JCOMPONENT.acc_credit_voucher_update_view, {
            url: '/acc_credit_voucher_update_view/:id',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/credit_voucher/credit_voucher_form.html',
            controller: 'AccCreditVoucherFormCtrl'
        }).state(JCOMPONENT.acc_credit_voucher_list_view, {
            url: '/acc_credit_voucher_list_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/credit_voucher/credit_voucher_list.html',
            controller: 'AccCreditVoucherListCtrl'
        })

        .state(JCOMPONENT.acc_journal_voucher_add_view, {
            url: '/acc_journal_voucher_add_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/journal_voucher/journal_voucher_form.html',
            controller: 'AccJournalVoucherFormCtrl'
        }).state(JCOMPONENT.acc_journal_voucher_update_view, {
            url: '/acc_journal_voucher_update_view/:id',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/journal_voucher/journal_voucher_form.html',
            controller: 'AccJournalVoucherFormCtrl'
        }).state(JCOMPONENT.acc_journal_voucher_list_view, {
            url: '/acc_journal_voucher_list_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/journal_voucher/journal_voucher_list.html',
            controller: 'AccJournalVoucherListCtrl'
        })

        .state(JCOMPONENT.acc_transfer_voucher_add_view, {
            url: '/acc_transfer_voucher_add_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/transfer_voucher/transfer_voucher_form.html',
            controller: 'AccTransferVoucherFormCtrl'
        }).state(JCOMPONENT.acc_transfer_voucher_update_view, {
            url: '/acc_transfer_voucher_update_view/:id',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/transfer_voucher/transfer_voucher_form.html',
            controller: 'AccTransferVoucherFormCtrl'
        }).state(JCOMPONENT.acc_transfer_voucher_list_view, {
            url: '/acc_transfer_voucher_list_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/transfer_voucher/transfer_voucher_list.html',
            controller: 'AccTransferVoucherListCtrl'
        })

        .state(JCOMPONENT.acc_chart_of_accounts_add_view, {
            url: '/acc_chart_of_accounts_add_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/setup/chart_of_accounts/chart_of_accounts_form.html',
            controller: 'AccChartOfAccountsFormCtrl'
        }).state(JCOMPONENT.acc_chart_of_accounts_update_view, {
            url: '/acc_chart_of_accounts_update_view/:id',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME +'/setup/chart_of_accounts/chart_of_accounts_form.html',
            controller: 'AccChartOfAccountsFormCtrl'
        }).state(JCOMPONENT.acc_chart_of_accounts_list_view, {
            url: '/acc_chart_of_accounts_list_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/setup/chart_of_accounts/chart_of_accounts_list.html',
            controller: 'AccChartOfAccountsListCtrl'
        })

        .state(JCOMPONENT.acc_sub_accounts_add_view, {
            url: '/acc_sub_accounts_add_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/setup/sub_accounts/sub_accounts_form.html',
            controller: 'AccSubAccountsFormCtrl'
        })

        .state(JCOMPONENT.acc_voucher_posting_list_view, {
            url: '/acc_voucher_posting_list_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/common/voucher_posting_list.html',
            controller: 'AccVoucherPostingListCtrl'
        })

        .state(JCOMPONENT.acc_report_account_wise_form_view, {
            url: '/acc_report_account_wise_form_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/report/account_ledger_report.form.html',
            controller: 'AccReportAccountLedgerFormCtrl'
        }).state(JCOMPONENT.acc_report_sub_account_wise_form_view, {
            url: '/acc_report_sub_account_wise_form_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/report/sub_account_ledger_report.form.html',
            controller: 'AccReportSubAccountLedgerFormCtrl'
        }).state(JCOMPONENT.acc_report_cus_sup_emp_wise_form_view, {
            url: '/acc_report_cus_sup_emp_wise_form_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/report/cus_sup_emp_ledger_report.form.html',
            controller: 'AccReportCusSupEmpLedgerFormCtrl'
        }).state(JCOMPONENT.acc_report_bank_form_view, {
            url: '/acc_report_bank_form_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/report/bank_ledger_report.form.html',
            controller: 'AccReportBankLedgerFormCtrl'
        }).state(JCOMPONENT.acc_trial_balance_form_view, {
            url: '/acc_trial_balance_form_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/report/trial_balance_report.form.html',
            controller: 'AccReportTrialBalanceFormCtrl'
        });

        $urlRouterProvider.otherwise('/' + JCOMPONENT.acc_debit_voucher_add_view);
        
    });
    