app.controller('AccJournalVoucherFormCtrl', function (
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
    $scope.particularsDivClassVar = "col-xs-12 col-md-6";

    $scope.coaList = [];
    $scope.subaccountList = [];
    $scope.bankList = [];
    $scope.voucher_details_list = [];
    $scope.selectedCoa = null;
    $scope.selectedSubCoa = null;

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
        paymentType: VOUCHER_KEY.PAYMENT_TYPE.JOURNAL,
        voucherPrefix: VOUCHER_KEY.PREFIX.JOURNAL_VOUCHER,
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
        debitAmount: "",
        creditAmount: "",
        amount: "",
        createdBy: 0,
        createOn: new Date()
    };

    $scope.isSubAccHide = true;
    $scope.detailsAddBtnHide = false;
    $scope.detailsEditBtnHide = true;


    $scope.getCoaList = function () {
        AccountsService.getCoaList().then(function (resp) {
            if (resp.code === 200) {
                $scope.coaList = resp.body.filter(it => it.accounts_usage !== 'Cash' && it.accounts_usage !== 'Bank');
//                $scope.getCashBankCoa();
            }
        });
    };

    $scope.getSubAccountList = function (accountsSource, chartOfAccountsId) {
        return AccountsService.getSubAccountList(accountsSource, chartOfAccountsId)
            .then(function (resp) {
                if (resp.code === 200) {
                    $scope.selectedSubCoa = null;
                    $scope.subaccountList = resp.body;
                }
            });
    };

    $scope.fetchChartOfAccounts = function(params) {
        return AccountsService.fetchPaginatedData($scope.coaList, params, 'coa_code_name');
    };

    $scope.fetchSubAccounts = function(params) {
        return AccountsService.fetchPaginatedData($scope.subaccountList, params, 'name');
    };

    $scope.onChartOfAccountsChange = function (coa) {
        $scope.subaccountList = [];

        var chartOfAccountsObj =  $scope.coaList.find(item => item.id === coa.id);
        $scope.voucher_details.chartOfAccountsId = chartOfAccountsObj.id;
        $scope.voucher_details.chartOfAccountsSource = chartOfAccountsObj.accounts_source;
        if (chartOfAccountsObj  && $scope.voucher_details.chartOfAccountsSource != 'None') {
            $scope.isSubAccHide = false;

            AccountsService.getSubAccountList(chartOfAccountsObj.accounts_source, coa.id)
                .then(function (resp) {
                    if (resp.code === 200) {
                        $scope.subaccountList = resp.body;
                        $scope.selectedSubCoa = null;
                    }
                });
        } else {
            $scope.isSubAccHide = true;
//            $scope.particularsDivClassVar = "col-xs-12 col-md-4";
            $scope.voucher_details.subAccountsId = $scope.subaccountList[0];
        }
    };

    $scope.addToGrid = function () {
        if (!$scope.isValidVoucherDetails()) return;

        const detail = $scope.buildDetailObject($scope.voucher_details);
        $scope.voucher_details_list.push(detail);

        const moduleDetail = angular.copy(detail);
        moduleDetail.row = $scope.module.details.length + 1;
        moduleDetail.currencyType = "BDT";
        moduleDetail.currencyRate = $scope.voucher_details.currencyRate || 1;
        $scope.module.details.push(moduleDetail);

        $scope.updateAmountInWords();
        $scope.resetVoucherDetails();
    };

    // ---------------------------
    // Debit, Credit balance show in table footer
    // ---------------------------

    $scope.getBalanceAmount = function () {
        const debit = $scope.getVoucherAmount(true);
        const credit = $scope.getVoucherAmount(false);
        return Math.abs(debit - credit);
    };

    $scope.getBalanceText = function () {
        if ($scope.voucher_details_list.length === 0) {
            return '';
        }

        const balance = $scope.getBalanceAmount();
        const html = balance === 0
            ? 'Balanced'
            : 'Difference: <strong>' + balance.toFixed(1) + '</strong>';
            return $sce.trustAsHtml(html);
    };

    $scope.getBalanceClass = function () {
        if ($scope.voucher_details_list.length === 0) {
            return '';
        }
        return $scope.getBalanceAmount() === 0 ? 'label label-success' : 'label label-danger';
    };

    // ---------------------------
    // Validation
    // ---------------------------
    $scope.isValidVoucherDetails = function () {
        const details = $scope.voucher_details;

        if (!details.chartOfAccountsId) {
            growl.error('Please select chart of accounts', { title: 'Validation Error!' });
            return false;
        }

        if ($scope.selectedCoa?.accounts_source !== 'None' && !$scope.selectedSubCoa) {
            growl.error('Please select sub-accounts', { title: 'Validation Error!' });
            return false;
        }

        if (!details.particulars) {
            growl.error('Please enter particulars', { title: 'Validation Error!' });
            return false;
        }

         if (
                (!details.debitAmount || Number(details.debitAmount) <= 0) &&
                (!details.creditAmount || Number(details.creditAmount) <= 0)
            ) {
            growl.error('Enter a debit or credit amount greater than 0', { title: 'Validation Error!' });
            return false;
        }

        return true;
    };

    // ---------------------------
    // Voucher Detail Object Builder
    // ---------------------------
    $scope.buildDetailObject = function (source) {
        const isDebit = !!source.debitAmount;
        const amount = isDebit ? source.debitAmount : source.creditAmount;
        const primeAmount = amount * (source.currencyRate || 1) * (isDebit ? 1 : -1);
        const baseAmount = isDebit ? amount : -amount;

        return {
            chartOfAccountsId: source.chartOfAccountsId,
            chartOfAccountsCodeName: $scope.selectedCoa.coa_code_name,
            subAccountsId: $scope.selectedSubCoa ? $scope.selectedSubCoa.id : '',
            subAccountsCodeName: $scope.selectedSubCoa ? $scope.selectedSubCoa.name : '',
            particulars: source.particulars,
            amount: amount,
            primeAmount: primeAmount,
            baseAmount: baseAmount
        };
    };

    // ---------------------------
    // Amount In Words + Footer Update
    // ---------------------------
    $scope.updateAmountInWords = function () {
        const debit = $scope.getVoucherAmount(true);
        const credit = $scope.getVoucherAmount(false);

        $("#amountInWords").val(amountToTextWithDecimal(debit));

        $('table tfoot td').eq($scope.table_debit_index).text(debit);
        $('table tfoot td').eq($scope.table_credit_index).text(credit);
    };

    $scope.getVoucherAmount = function (isDebit) {
        const sum = $scope.voucher_details_list.reduce((acc, item) => {
            return acc + ((isDebit && item.primeAmount > 0) || (!isDebit && item.primeAmount < 0)
                ? Math.abs(item.primeAmount)
                : 0);
        }, 0);

        $scope.module.amount = sum;
        return sum;
    };

    // ---------------------------
    // Reset Form
    // ---------------------------
    $scope.resetVoucherDetails = function () {
        $scope.voucher_details = {
            chartOfAccountsId: "",
            subAccountsId: "",
            particulars: "",
            debitAmount: "",
            creditAmount: "",
            currencyRate: 1
        };
        $scope.isSubAccHide = true;
        $scope.selectedCoa = null;
        $scope.selectedSubCoa = null;
    };

    // ---------------------------
    // Save/Update and Validation Logic
    // ---------------------------

    $scope.saveModule = function () {
        if (!$scope.isModuleValid()) return;
        if (!$scope.validateBeforeSave())return;

        const isUpdate = $state.current.name === JCOMPONENT.acc_journal_voucher_update_view;
        const method = isUpdate ? "PUT" : "POST";
        const url = isUpdate ? API.ACC_JOURNAL_VOUCHER_UPDATE : API.ACC_JOURNAL_VOUCHER_SAVE;

        Communication.request(method, url, $scope.module).then(function (resp) {
            if (resp.code === 200) {
                growl.success('Successfully saved', { title: 'Success!' });
                $scope.reset();
                $scope.module.details = [];

                if (isUpdate) {
                    $state.go(JCOMPONENT.acc_journal_voucher_list_view);
                    ToasterMessageQueueService.addMessage('success', 'Successfully updated', 'Success!');
                }
            } else {
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            $rootScope.toastError(err.message);
        });
    };

    $scope.isModuleValid = function () {
        if (!$scope.module.particulars) {
            growl.error('Please enter particulars', { title: 'Validation Error!' });
            return false;
        }

        if (!$scope.module.details.length) {
            growl.error('Please enter voucher details', { title: 'Validation Error!' });
            return false;
        }

        const total = $scope.module.details.reduce((sum, d) => sum + Number(d.baseAmount), 0);
        if (total !== 0) {
            growl.error('Debit credit amount not equal', { title: 'Validation Error!' });
            return false;
        }

        return true;
    };

    $scope.validateBeforeSave = function () {
        let debitSum = $scope.voucher_details_list.reduce((sum, d) =>
            sum + ((d.primeAmount > 0) ? Number(d.amount || 0) : 0), 0);

        let creditSum = $scope.voucher_details_list.reduce((sum, d) =>
            sum + ((d.primeAmount < 0) ? Number(d.amount || 0) : 0), 0);


        if (debitSum <= 0) {
            growl.error("Total debit amount must be greater than 0", { title: 'Validation Error' });
            return false;
        }

        if (creditSum <= 0) {
            growl.error("Total credit amount must be greater than 0", { title: 'Validation Error' });
            return false;
        }

        if (debitSum.toFixed(2) !== creditSum.toFixed(2)) {
            const diff = Math.abs(debitSum - creditSum).toFixed(2);
            growl.error(`Debit and credit amounts must be equal. Difference: ${diff}`, { title: 'Validation Error' });
            return false;
        }

        return true;
    };


    // ---------------------------
    // Load Voucher in Edit Mode
    // ---------------------------

    if ($state.current.name === JCOMPONENT.acc_journal_voucher_update_view) {
        Communication.request("GET", API.ACC_JOURNAL_VOUCHER_GET + '/' + $stateParams.id).then(function (resp) {
            if (resp.code === 200) {
                $scope.module = resp.body;
                $scope.loadVoucherGridFromModuleDetails($scope.module.details);
                $scope.updateAmountInWords();
            }
        });
    }

    $scope.loadVoucherGridFromModuleDetails = function (details) {
        $scope.voucher_details_list = details.map(d => ({
            id: d.id,
            chartOfAccountsId: d.chartOfAccountsId,
            chartOfAccountsCodeName: d.chartOfAccountsCodeName,
            chartOfAccountsSource: d.chartOfAccountsSource,
            subAccountsId: d.subAccountsId || "",
            subAccountsCodeName: d.subAccountsCodeName || "",
            particulars: d.particulars,
            amount: d.amount,
            primeAmount: d.primeAmount,
            baseAmount: d.baseAmount
        }));
    };

    // ---------------------------
    // Row Editing Logic
    // ---------------------------

    $scope.populateRowData = function (rowData, index) {
        $scope.rowIndex = index;
        $scope.detailsAddBtnHide = true;
        $scope.detailsEditBtnHide = false;

        $scope.selectedCoa = $scope.coaList.find(obj => obj.id === rowData.chartOfAccountsId);

        Object.assign($scope.voucher_details, {
            id: rowData.id,
            particulars: rowData.particulars,
            amount: rowData.amount,
            debitAmount: rowData.primeAmount > 0 ? rowData.amount : "",
            creditAmount: rowData.primeAmount < 0 ? rowData.amount : "",
            chartOfAccountsId: rowData.chartOfAccountsId,
            chartOfAccountsSource: $scope.selectedCoa.accounts_source
        });

        if ($scope.selectedCoa.accounts_source === 'None' && $scope.selectedCoa.accounts_usage != 'Bank') {
            $scope.isSubAccHide = true;
            $scope.selectedSubCoa = null;
            $scope.voucher_details.subAccountsId = "";
        } else {
            return $scope.getSubAccountList($scope.selectedCoa.accounts_source, rowData.chartOfAccountsId)
                .then(function () {
                    $scope.selectedSubCoa = $scope.subaccountList.find(obj => obj.id === rowData.subAccountsId);
                    $scope.voucher_details.subAccountsId = $scope.selectedSubCoa?.id;
                    $scope.isSubAccHide = false;
                });
        }
    };

    // ---------------------------
    // Edit and Update Grid
    // ---------------------------

    $scope.editToGrid = function () {
        $scope.detailsAddBtnHide = false;
        $scope.detailsEditBtnHide = true;

        const updated = $scope.buildDetailObject($scope.voucher_details);
        updated.id = $scope.voucher_details.id;

        $scope.voucher_details_list[$scope.rowIndex] = updated;
        $scope.updateModuleDetails(updated);
        $scope.updateAmountInWords();
        $scope.resetVoucherDetails();
    };

    $scope.updateModuleDetails = function (updated) {
        const index = $scope.rowIndex;
        const matchById = d => d.id == updated.id;

        const target = updated.id !== undefined
            ? $scope.module.details.find(matchById)
            : $scope.module.details[index];

        Object.assign(target, updated);
    };

    // ---------------------------
    // Delete Row Logic
    // ---------------------------

    $scope.deleteRow = function (index) {
        $scope.voucher_details_list.splice(index, 1);
        $scope.module.details.splice(index, 1);
        $scope.updateAmountInWords();
    };

    $scope.resetTable = function () {
        var table = document.getElementById("voucher-details-table");

        while (table.rows.length-1 > 1) {
            table.deleteRow(1);
        }
    };

    $scope.reset = function () {
        $scope.voucher_details_list = [];
        $scope.bankList = [];
        $scope.coaList = [];
        $scope.subaccountList = [];
        $scope.isSubAccHide = true;
        $scope.selectedCoa = null;
        $scope.selectedSubCoa = null;
        $scope.module.particulars = "";
        $scope.module.chequeNo = "";
        $scope.module.chequeDate = "";
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