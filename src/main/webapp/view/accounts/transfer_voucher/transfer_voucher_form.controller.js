app.controller('AccTransferVoucherFormCtrl', function (
                $scope, $http, $state, $timeout, $stateParams, $rootScope, $sce,
                $mdDialog, $interval, ClientService, DialogBox, encrypt,
                Communication, $filter, growl, ToasterMessageQueueService, $q,
                DateHelperService, AccountsService
            ) {

    copyPasteStringRestrict('.pastedString');
    $rootScope.setPageName(JMODULE_NAME, $state.current.name);
    $scope.current_state = $state.current.name;

    $scope.table_debit_index = 3;
    $scope.table_credit_index = 4;
    $scope.particularsDivClassVar = "col-xs-12 col-md-4";

    $scope.coaList = [];
    $scope.subaccountList = [];
    $scope.bankList = [];
    $scope.voucher_details_list = [];
    $scope.selectedCoa = null;
    $scope.selectedSubCoa = null;
    $scope.selectedBankAccount = null;

    $scope.cash_bank_coa_obj = {
        id: "",
        coaNameAndCode: ""
    };

    $scope.module = {
        id:"",
        bankAccountId: "",
        voucherNo: "",
        reference: "",
        voucherDate: new Date(),
        particulars: "",
        year: "",
        month: "",
        status: "",
        voucherType: "",
        paymentType: VOUCHER_KEY.PAYMENT_TYPE.CASH,
        voucherPrefix: VOUCHER_KEY.PREFIX.TRANSFER_VOUCHER,
        chequeNo: "",
        chequeDate: "",
        chequeStatus: "",
        action: "",
        amount: "",
        createdBy: 0,
        createOn: new Date(),
        details: []
    };

    $scope.voucher_details = {
        chartOfAccountsId: "",
        subAccountsId: "",
        subAccountsCode: "",
        chartOfAccountsSource: "",
        chartOfAccountsUsage: "Cash",
        debitCreditFlag: "",
        particulars: "",
        currencyType: "BDT",
        currencyRate: 1,
        amount: "",
        createdBy: 0,
        createOn: new Date()
    };

    $scope.isBankHide = true;
    $scope.isSubAccHide = true;
    $scope.detailsAddBtnHide = false;
    $scope.detailsEditBtnHide = true;


    $scope.getCoaList = function () {
        AccountsService.getCoaList().then(function (resp) {
            if (resp.code === 200) {
                $scope.coaList = resp.body.filter(it => it.accounts_usage === 'Cash' || it.accounts_usage === 'Bank');
                $scope.getCashBankCoa();
            }
        });
    };

    $scope.getCashBankCoa = function () {
        return AccountsService.getCashBankCoa($scope.voucher_details.chartOfAccountsUsage)
            .then(function (resp) {
                if (resp.code === 200) {
                    $scope.cash_bank_coa_obj.id = resp.body.id;
                    $scope.cash_bank_coa_obj.coaNameAndCode = resp.body.coa_code_name;
                }
            });
    };

    $scope.getSubAccountList = function (accountsSource, chartOfAccountsId) {
        return Communication.request("GET", API.BANK_ACCOUNT_LIST, {})
            .then(function (resp) {
            log("Sub account list: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.subaccountList = resp.body;
            }

        }, function (err) {
            log("Sub account list error", JSON.stringify(err));
        });
    };

    $scope.getBankAccountList = function () {
        AccountsService.getBankAccountList().then(function (resp) {
            if (resp.code === 200) {
                $scope.bankAccountList = resp.body;
            }
        });
    };

    $scope.fetchChartOfAccounts = function(params) {
        return AccountsService.fetchPaginatedData($scope.coaList, params, 'coa_code_name');
    };

    $scope.fetchSubAccounts = function(params) {
        return AccountsService.fetchPaginatedData($scope.subaccountList, params, 'name');
    };

    $scope.fetchBankAccount = function(params) {
        return AccountsService.fetchPaginatedData($scope.bankAccountList, params, 'name');
    };

    $scope.onChartOfAccountsChange = function (coa) {
        $scope.subaccountList = [];

        var chartOfAccountsObj =  $scope.coaList.find(item => item.id === coa.id);
        $scope.voucher_details.chartOfAccountsId = chartOfAccountsObj.id;
        var accountsUsage = chartOfAccountsObj.accounts_usage;
        $scope.voucher_details.chartOfAccountsSource =
                accountsUsage.toLocaleUpperCase() === VOUCHER_KEY.ACCOUNTS_USAGE.BANK ? accountsUsage : chartOfAccountsObj.accounts_source;
        if (chartOfAccountsObj  && accountsUsage.toLocaleUpperCase() === VOUCHER_KEY.ACCOUNTS_USAGE.BANK) {
            $scope.isSubAccHide = false;

            $scope.getSubAccountList(chartOfAccountsObj.accounts_source, coa.id)
                .then(function (resp) {
                    if (resp.code === 200) {
                        $scope.subaccountList = resp.body;
                        $scope.selectedSubCoa = null;
                    }
                });
        } else {
            $scope.isSubAccHide = true;
            $scope.particularsDivClassVar = "col-xs-12 col-md-4";
            $scope.voucher_details.subAccountsId = $scope.subaccountList[0];
        }
    };

    $scope.onBankAccountChange = function (bankAccount) {
        $scope.module.bankAccountId = bankAccount.id;

        if($scope.module.details.length > 0) {
            const details = $scope.module.details[0];
            details.subAccountsId = bankAccount.id;
            details.subAccountsCodeName = bankAccount.name;

            $scope.voucher_details_list[0] = angular.copy(details);
        }
    }

    $scope.selectPaymentType = function (pay_typ) {
        $scope.voucher_details.chartOfAccountsUsage = pay_typ;

        $scope.getCashBankCoa(pay_typ).then(function () {
            $scope.isBankHide = (pay_typ !== VOUCHER_KEY.PAYMENT_TYPE.BANK);

            if (pay_typ === VOUCHER_KEY.PAYMENT_TYPE.BANK) {
                $scope.getBankAccountList();
            }

            if ($scope.module.details.length > 0) {
                const details = $scope.module.details[0];

                // Always set these
                details.chartOfAccountsId = $scope.cash_bank_coa_obj.id;
                details.chartOfAccountsCodeName = $scope.cash_bank_coa_obj.coaNameAndCode;

                // Conditionally set sub-account
                if (pay_typ === VOUCHER_KEY.PAYMENT_TYPE.BANK && $scope.selectedBankAccount) {
                    details.subAccountsId = $scope.selectedBankAccount.id;
                    details.subAccountsCodeName = $scope.selectedBankAccount.name;
                } else {
                    details.subAccountsId = '';
                    details.subAccountsCodeName = '';
                    $scope.module.chequeNo = "";
                    $scope.module.chequeDate = "";
                }

                // Reflect update in voucher_details_list[0]
                if ($scope.voucher_details_list.length > 0) {
                    $scope.voucher_details_list[0] = angular.copy(details);
                }
            }
        });
    };

    $scope.addToGrid = function () {
        if (!$scope.gridDataValidation()) return;

        $scope.addGridEntries();
        $scope.addVoucherEntries();
        $scope.updateAmountInWords();
        $scope.clearVoucherInputFields();
    };

    $scope.gridDataValidation = function () {
        return AccountsService.gridDataValidation(
            $scope.voucher_details,
            $scope.selectedCoa,
            $scope.selectedSubCoa,
            growl
        );
    };

    $scope.addGridEntries = function () {
        const data = {
            paymentType: $scope.module.paymentType,
            cash_bank_coa_obj: $scope.cash_bank_coa_obj,
            selectedCoa: $scope.selectedCoa,
            selectedSubCoa: $scope.selectedSubCoa,
            selectedBankAccount: $scope.selectedBankAccount,
            voucher_details: $scope.voucher_details,
        };

        if ($scope.voucher_details_list.length === 0) {
            $scope.voucher_details_list.push(AccountsService.createEntryObject(true, data));
        } else {
            $scope.updateAmountInWords();
        }

        $scope.voucher_details_list.push(AccountsService.createEntryObject(false, data));
    };

    $scope.addVoucherEntries = function () {
        const data = {
            paymentType: $scope.module.paymentType,
            cash_bank_coa_obj: $scope.cash_bank_coa_obj,
            selectedCoa: $scope.selectedCoa,
            selectedSubCoa: $scope.selectedSubCoa,
            selectedBankAccount: $scope.selectedBankAccount,
            voucher_details: $scope.voucher_details,
            module: $scope.module,
        };

        if ($scope.module.details.length === 0) {
            const vEntry = AccountsService.createVoucherEntryObject(true, data);
            vEntry.row = 1;
            $scope.module.details.push(vEntry);
            $scope.module.amount = $scope.voucher_details.amount;
        } else {
            const creditAmount = $scope.voucherAmount();
            const vEntry = $scope.module.details[0];
            vEntry.amount = creditAmount;
            vEntry.primeAmount = -creditAmount * $scope.voucher_details.currencyRate;
            vEntry.baseAmount = -creditAmount;
            $scope.module.amount = creditAmount;
        }

        const vDetails = AccountsService.createVoucherEntryObject(false, data);
        vDetails.row = $scope.module.details.length + 1;
        $scope.module.details.push(vDetails);
    };

    $scope.updateAmountInWords = function () {
        const amount = $scope.voucherAmount();
        $("#amountInWords").val(amountToTextWithDecimal(amount));
        $('table tfoot td').eq($scope.table_debit_index).text(amount);
        $('table tfoot td').eq($scope.table_credit_index).text(amount);
    };

    $scope.clearVoucherInputFields = function () {
        AccountsService.clearVoucherInputFields($scope);
    };

    $scope.voucherAmount = function () {
        return AccountsService.voucherAmount(
            $scope.voucher_details_list,
            $scope.voucher_details.currencyRate,
            $scope.table_debit_index,
            $scope.table_credit_index,
            $scope.module
        );
    };

    $scope.saveModule = function () {
        const isValid = AccountsService.validateVoucherBeforeSave(
            $scope.module,
            $scope.selectedBankAccount,
            growl,
            VOUCHER_KEY
        );

        if (!isValid) return;

        //Avoid time zone
        DateHelperService.formatMultipleFields($scope.module, ['chequeDate', 'voucherDate']);

        const method = ($state.current.name === JCOMPONENT.acc_transfer_voucher_update_view) ? "PUT" : "POST";
        const url = ($state.current.name === JCOMPONENT.acc_transfer_voucher_update_view) ? API.ACC_TRANSFER_VOUCHER_UPDATE : API.ACC_TRANSFER_VOUCHER_SAVE;

        Communication.request(method, url, $scope.module)
            .then(function (resp) {
                if (resp.code === 200) {
                    growl.success('Successfully saved',{title: 'Success!'});
                    $scope.reset();
                    $scope.module.details = [];
                    $scope.module.paymentType = VOUCHER_KEY.PAYMENT_TYPE.CASH;

                    if($state.current.name === JCOMPONENT.acc_transfer_voucher_update_view){
                        $state.go(JCOMPONENT.acc_transfer_voucher_list_view);
                        ToasterMessageQueueService.addMessage('success', 'Successfully updated', 'Success!');
                    } else {
                        growl.success('Successfully saved',{title: 'Success!'});
                    }
                } else{
                    $rootScope.toastError(resp.message);
                }
            }, function (err) {
                log("Transfer voucher error", JSON.stringify(err));
                $rootScope.toastError(err.message);
            }).finally(function () {
                AccountsService.parseDatesIfString($scope.module, ['voucherDate', 'chequeDate']);
            });
    };

    if ($state.current.name === JCOMPONENT.acc_transfer_voucher_update_view) {
        $scope.getBankAccountList();
        AccountsService.loadVoucherForEdit($scope, $stateParams, Communication, API, log);
    }

    $scope.rowDataPopulate = function (rowData, rowIndex) {
        AccountsService.populateRowData($scope, rowData, rowIndex);
    };

    $scope.editToGrid = function () {
        AccountsService.updateRow($scope);
    };

    $scope.deleteRow = function (index) {
        AccountsService.deleteRow($scope, index);
    };

    $scope.resetTable = function () {
        const table = document.getElementById("voucher-details-table");
        while (table.rows.length - 1 > 1) {
            table.deleteRow(1);
        }
    };

    $scope.reset = function () {
        $scope.voucher_details_list = [];
        $scope.bankList = [];
        $scope.coaList = [];
        $scope.subaccountList = [];
        $scope.isBankHide = true;
        $scope.isSubAccHide = true;

        $scope.selectedCoa = null;
        $scope.selectedSubCoa = null;
        $scope.selectedBankAccount = null;

        $scope.module.voucherDate = new Date();
        $scope.module.particulars = "";
        $scope.module.chequeNo = "";
        $scope.module.chequeDate = "";
        $scope.particularsDivClassVar = "col-xs-12 col-md-4";

        $('#amountInWords').val("");
        $('table tfoot td').eq($scope.table_debit_index).text(0);
        $('table tfoot td').eq($scope.table_credit_index).text(0);

        $scope.getCoaList();
        $scope.resetTable();
    };

    $scope.resetForm = function () {
        $scope.module.paymentType = VOUCHER_KEY.PAYMENT_TYPE.CASH;
        $scope.reset();
    };
});