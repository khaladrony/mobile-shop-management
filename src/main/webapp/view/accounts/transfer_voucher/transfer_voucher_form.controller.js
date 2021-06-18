app.controller('AccTransferVoucherFormCtrl', function ($scope, $http, $state, $timeout, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService, DialogBox, encrypt, Communication,$filter,growl) {

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
    $scope.cash_bank_coa_obj = {
        id: "",
        coaNameAndCode: ""
    };

    $scope.module = {
        id:"",
        bankAccountId: "",
        voucherNo: "",
        reference: "",
        voucherDate: "",
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
        var req = Communication.request("GET", API.ACC_CHART_OF_ACCOUNTS_LIST_DROP_DOWN, {});
        req.then(function (resp) {
            log("Chart of accounts list: " + JSON.stringify(resp));

            if (resp.code === 200) {
                if($scope.module.paymentType === VOUCHER_KEY.PAYMENT_TYPE.CASH){
                    $scope.coaList = resp.body.filter((it) => it.accounts_usage === 'Bank');
                } else {
                    $scope.coaList = resp.body.filter((it) => it.accounts_usage === 'Cash' || it.accounts_usage === 'Bank');
                }

                $scope.getCashBankCoa();
            }

        }, function (err) {
            log("Chart of accounts error", JSON.stringify(err));
        });
    };

    $scope.selectChartOfAccounts = function (coaId) {

        $scope.subaccountList = [];
        var chartOfAccountsObj =  $scope.coaList.filter(item => item.id === coaId);
        $scope.voucher_details.chartOfAccountsSource = chartOfAccountsObj[0].accounts_source;
        var accountsUsage = chartOfAccountsObj[0].accounts_usage;
        if (chartOfAccountsObj.length > 0 && accountsUsage.toLocaleUpperCase() === VOUCHER_KEY.ACCOUNTS_USAGE.BANK) {
            $scope.isSubAccHide = false;
            $scope.getSubAccountList($scope.voucher_details.chartOfAccountsSource, coaId);
            $scope.particularsDivClassVar = "col-xs-12 col-md-3";
        } else {
            $scope.isSubAccHide = true;
            $scope.particularsDivClassVar = "col-xs-12 col-md-6";
            $scope.voucher_details.subAccountsId = $scope.subaccountList[0];
        }
    };

    $scope.getSubAccountList = function (accountsSource, chartOfAccountsId) {

        // var req = Communication.request("GET", API.ACC_GET_SUB_ACCOUNTS_LIST + '/' + accountsSource + '/' + chartOfAccountsId, {});
        var req = Communication.request("GET", API.BANK_ACCOUNT_LIST, {});
        req.then(function (resp) {
            log("Sub account list: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.subaccountList = resp.body;
            }

        }, function (err) {
            log("Sub account list error", JSON.stringify(err));
        });
    };

    $scope.getBankAccountList = function () {

        var req = Communication.request("GET", API.BANK_ACCOUNT_LIST, {});
        req.then(function (resp) {
            log("bank account list: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.bankAccountList = resp.body;
            }

        }, function (err) {
            log("Bank account list error", JSON.stringify(err));
        });
    };

    $scope.selectPaymentType = function (pay_typ) {

        $scope.voucher_details.chartOfAccountsUsage = pay_typ;
        $scope.reset();

        if (pay_typ === VOUCHER_KEY.PAYMENT_TYPE.BANK) {
            $scope.isBankHide = false;
            $scope.getBankAccountList();
        } else {
            $scope.isBankHide = true;
        }
        $scope.getCashBankCoa();
    };

    $scope.getCashBankCoa = function () {
        var req = Communication.request("GET", API.ACC_CHART_OF_ACCOUNTS_BY_USAGES_TYPE + '/' + $scope.voucher_details.chartOfAccountsUsage);
        req.then(function (resp) {
            log("Chart of accounts by usage type: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.cash_bank_coa_obj.id = resp.body.id;
                $scope.cash_bank_coa_obj.coaNameAndCode = resp.body.coa_code_name;
            }

        }, function (err) {
            log("Chart of accounts by usage type error", JSON.stringify(err));
        });
    };

    $scope.addToGrid = function () {

        if (!$scope.gridDataValidation()) {
            return;
        }

        $scope.gridObj();

        $scope.voucherObj();

        $scope.amountInWords();

        $scope.clearTaxBox();

    };

    $scope.gridDataValidation = function () {

        if ($scope.voucher_details_list.length === 2) {
            growl.error('You cannot add more record.',{title: 'Error!'});
            return false;
        }

        if ($scope.voucher_details.chartOfAccountsId === '') {
            growl.error('Please select chart of accounts',{title: 'Error!'});
            return false;
        }

        var chartOfAccountsObj = $scope.coaList.filter(item => item.id === $scope.voucher_details.chartOfAccountsId);
        $scope.voucher_details.chartOfAccountsSource = chartOfAccountsObj[0].accounts_source;
        var accountsUsage = chartOfAccountsObj[0].accounts_usage;
        if (chartOfAccountsObj.length > 0 && accountsUsage.toLocaleUpperCase() === VOUCHER_KEY.ACCOUNTS_USAGE.BANK
            && $scope.voucher_details.subAccountsId==='') {
            growl.error('Please select bank a/c',{title: 'Error!'});
            return false;
        }

        if ($scope.voucher_details.particulars === '') {
            growl.error('Please enter particulars',{title: 'Error!'});
            return false;
        }

        if ($scope.voucher_details.amount === '') {
            growl.error('Please enter amount',{title: 'Error!'});
            return false;
        }

        if ($scope.voucher_details.subAccountsId === $scope.module.bankAccountId) {
            growl.error('Same bank a/c transfer not possible', {title: 'Error!'});
            return false;
        }

        return true;
    };


    $scope.gridObj = function () {

        $scope.gridCashBankObj();

        $scope.gridNonCashBankObj();
    };

    $scope.voucherObj = function () {

        $scope.voucherCashBankObj();

        $scope.voucherNonCashBankObj();

    };

    $scope.gridCashBankObj = function () {
        var details = [];

        if ($scope.voucher_details_list.length === 0) {
            details.chartOfAccountsId = $scope.cash_bank_coa_obj.id;
            details.chartOfAccountsCodeName = $scope.cash_bank_coa_obj.coaNameAndCode;
            details.particulars = $scope.module.particulars;
            details.amount = $scope.voucher_details.amount;
            details.primeAmount =  (-1) * $scope.voucher_details.amount * $scope.voucher_details.currencyRate;
            details.baseAmount = (-1) * $scope.voucher_details.amount;
            if($scope.module.paymentType === VOUCHER_KEY.PAYMENT_TYPE.BANK){
                details.subAccountsId = $scope.module.bankAccountId;
                details.subAccountsCodeName = $scope.dropDownSelectText("bankAccountId");
            }

            $scope.voucher_details_list.push(details);

        }
    };

    $scope.gridNonCashBankObj = function () {
        var details = [];

        details.chartOfAccountsId = $scope.voucher_details.chartOfAccountsId;
        details.chartOfAccountsCodeName = $scope.dropDownSelectText("chartOfAccountsId");

        if ($scope.voucher_details.subAccountsId != "" && $scope.voucher_details.subAccountsId != undefined) {
            details.subAccountsId = $scope.voucher_details.subAccountsId;
            details.subAccountsCodeName = $scope.dropDownSelectText("subAccountsId");
        }

        details.particulars = $scope.voucher_details.particulars;
        details.amount = $scope.voucher_details.amount;
        details.primeAmount = $scope.voucher_details.amount * $scope.voucher_details.currencyRate;
        details.baseAmount = $scope.voucher_details.amount;

        $scope.voucher_details_list.push(details);

    };

    $scope.voucherCashBankObj = function () {
        var vDetails = {
            row:"",
            chartOfAccountsId: "",
            chartOfAccountsCodeName: "",
            subAccountsId: "",
            subAccountsCodeName: "",
            particulars: "",
            amount: "",
            primeAmount: "",
            baseAmount: "",
            currencyType: "BDT",
            currencyRate: 1
        };

        if ($scope.module.details.length === 0) {
            vDetails.chartOfAccountsId = $scope.cash_bank_coa_obj.id;
            vDetails.chartOfAccountsCodeName = $scope.cash_bank_coa_obj.coaNameAndCode;

            if ($scope.module.paymentType === VOUCHER_KEY.PAYMENT_TYPE.BANK) {
                vDetails.subAccountsId = $scope.module.bankAccountId;
                vDetails.subAccountsCodeName =  $scope.dropDownSelectText("bankAccountId");
                $scope.module.chequeStatus = VOUCHER_KEY.CHEQUE_STATUS.NOT_CLEARED;
            }
            vDetails.particulars = $scope.voucher_details.particulars;
            vDetails.amount = $scope.voucher_details.amount;
            vDetails.primeAmount = (-1) * $scope.voucher_details.amount * $scope.voucher_details.currencyRate;
            vDetails.baseAmount = (-1) * $scope.voucher_details.amount;
            vDetails.row = 1;
            $scope.module.details.push(vDetails);

            $scope.module.amount = $scope.voucher_details.amount;
        } else {
            var creditAmount = $scope.voucherAmount();

            $scope.module.details[0].amount = creditAmount;
            $scope.module.details[0].primeAmount = (-1) * creditAmount * $scope.voucher_details.currencyRate;
            $scope.module.details[0].baseAmount = (-1) * creditAmount;
            $scope.module.amount = creditAmount;
        }
    };

    $scope.voucherNonCashBankObj = function () {
        vDetails = {
            row:"",
            chartOfAccountsId: "",
            chartOfAccountsCodeName: "",
            subAccountsId: "",
            subAccountsCodeName: "",
            particulars: "",
            amount: "",
            primeAmount: "",
            baseAmount: "",
            currencyType: "BDT",
            currencyRate: 1
        };
        vDetails.chartOfAccountsId = $scope.voucher_details.chartOfAccountsId;
        vDetails.chartOfAccountsCodeName = $scope.dropDownSelectText("chartOfAccountsId");

        if ($scope.voucher_details.subAccountsId != "" && $scope.voucher_details.subAccountsId != undefined) {
            vDetails.subAccountsId = $scope.voucher_details.subAccountsId;
            vDetails.subAccountsCodeName =  $scope.dropDownSelectText("subAccountsId");
        }
        vDetails.particulars = $scope.voucher_details.particulars;
        vDetails.amount = $scope.voucher_details.amount;
        vDetails.primeAmount = $scope.voucher_details.amount * $scope.voucher_details.currencyRate;
        vDetails.baseAmount = $scope.voucher_details.amount;
        vDetails.row = $scope.module.details.length + 1;
        $scope.module.details.push(vDetails);
    };

    $scope.dropDownSelectText = function (elementid) {
        var s1 = document.getElementById(elementid);
        var text = s1.options[s1.selectedIndex].text.substring(0);
        return text;
    };

    $scope.amountInWords = function () {
        var voucherAmount = $scope.voucherAmount();
        var amountToConvert = amountToTextWithDecimal(voucherAmount);
        $("#amountInWords").val(amountToConvert);

        $('table tfoot td').eq($scope.table_debit_index).text(voucherAmount);
        $('table tfoot td').eq($scope.table_credit_index).text(voucherAmount);
    };

    $scope.clearTaxBox = function () {
        $scope.voucher_details.chartOfAccountsId = "";
        $scope.voucher_details.particulars = "";
        $scope.voucher_details.amount = "";
        $scope.isSubAccHide = true;
        $scope.particularsDivClassVar = "col-xs-12 col-md-6";
    };

    $scope.voucherAmount = function(){
        var voucherAmount = $scope.voucher_details_list.reduce(function (sum, voucherDetailObj) {
            if (voucherDetailObj.primeAmount < 0) {
                return sum + Math.abs(voucherDetailObj.primeAmount);
            } else {
                return Math.abs(sum);
            }
        }, 0);

        $scope.voucher_details_list[0].amount = voucherAmount;
        $scope.voucher_details_list[0].primeAmount = (-1) * voucherAmount * $scope.voucher_details.currencyRate;
        $scope.voucher_details_list[0].baseAmount = (-1) * voucherAmount;
        $scope.module.amount = voucherAmount;

        return voucherAmount;
    };


    $scope.saveModule = function () {

        if(!$scope.saveValidation()){
            return;
        }

        var req;
        if($state.current.name === JCOMPONENT.acc_transfer_voucher_update_view){
            req = Communication.request("PUT", API.ACC_TRANSFER_VOUCHER_UPDATE, $scope.module);
        } else{
            req = Communication.request("POST", API.ACC_TRANSFER_VOUCHER_SAVE, $scope.module);
        }

        req.then(function (resp) {
            log("transfer voucher: " + JSON.stringify(resp));
            if (resp.code === 200) {
                // $scope.module = resp.body;
                growl.success('Successfully saved',{title: 'Success!'});
                $scope.reset();
                $scope.module.details = [];
                $scope.module.paymentType = VOUCHER_KEY.PAYMENT_TYPE.CASH;

                if($state.current.name === JCOMPONENT.acc_transfer_voucher_update_view){
                    $state.go(JCOMPONENT.acc_transfer_voucher_list_view);
                    $rootScope.toastSuccess("Successfully saved");
                }

            } else{
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            log("transfer voucher error", JSON.stringify(err));
            $rootScope.toastError(err.message);
        });
    };

    $scope.saveValidation = function () {
        if ($scope.module.paymentType === '') {
            growl.error('Please select payment type',{title: 'Error!'});
            return false;
        }

        if ($scope.module.particulars === '') {
            growl.error('Please enter particulars',{title: 'Error!'});
            return false;
        }

        if($scope.module.paymentType === VOUCHER_KEY.PAYMENT_TYPE.BANK){
            if ($scope.module.bankAccountId === '') {
                growl.error('Please select bank a/c',{title: 'Error!'});
                return false;
            }
            if ($scope.module.chequeNo === '') {
                growl.error('Please enter cheque no',{title: 'Error!'});
                return false;
            }
            if ($scope.module.chequeDate === '') {
                growl.error('Please enter cheque date',{title: 'Error!'});
                return false;
            }

            if ($scope.voucher_details.subAccountsId === $scope.module.bankAccountId) {
                growl.error('Same bank a/c transfer not possible', {title: 'Error!'});
                return false;
            }
        }

        if ($scope.module.details.length === 0) {
            growl.error('Please enter voucher details',{title: 'Error!'});
            return false;
        }

        var voucherAmount = $scope.module.details.reduce(function (sum, voucherDetailObj) {
            return sum + Number(voucherDetailObj.baseAmount);
        }, 0);
        if (voucherAmount != 0) {
            growl.error('Debit credit amount not equal',{title: 'Error!'});
            return false;
        }

        return true;
    };

    if($state.current.name === JCOMPONENT.acc_transfer_voucher_update_view){

        $scope.$watch('module.chequeDate', function (newValue) {
            $scope.module.chequeDate = $filter('date')(newValue, 'yyyy-MM-dd');
        });

        var req = Communication.request("GET", API.ACC_TRANSFER_VOUCHER_GET + '/' + $stateParams.id, $scope.module);
        req.then(function (resp) {
            log("voucher edit: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;

                $scope.getGridVoucherDetailsForEdit($scope.module.details);

                $scope.amountInWords();
            }
        }, function (err) {
            log("voucher edit error", JSON.stringify(err));
        });
    }

    $scope.getGridVoucherDetailsForEdit = function (voucherDetails) {

        var details = [];
        angular.forEach(voucherDetails, function (value, key) {
            details.chartOfAccountsId = value.chartOfAccountsId;
            details.chartOfAccountsCodeName = value.chartOfAccountsCodeName;
            details.chartOfAccountsSource = value.chartOfAccountsSource;

            if (value.subAccountsId != null) {
                details.subAccountsId = value.subAccountsId;
                details.subAccountsCodeName = value.subAccountsCodeName;
            }

            details.particulars = value.particulars;
            details.amount = value.amount;
            details.primeAmount = value.primeAmount;
            details.baseAmount = value.baseAmount;
            details.id = value.id;

            $scope.voucher_details_list.push(details);
            details = [];
        });

        if ($scope.module.paymentType === VOUCHER_KEY.PAYMENT_TYPE.BANK) {
            $scope.isBankHide = false;
            $scope.getBankAccountList();
        } else {
            $scope.isBankHide = true;
        }

    };

    $scope.rowDataPopulate = function (rowData, rowIndex) {

        $scope.rowIndex = rowIndex;
        $scope.detailsAddBtnHide = true;
        $scope.detailsEditBtnHide = false;

        $scope.voucher_details.chartOfAccountsId = rowData.chartOfAccountsId;
        $scope.voucher_details.chartOfAccountsSource = rowData.chartOfAccountsSource;
        $scope.voucher_details.particulars = rowData.particulars;
        $scope.voucher_details.amount = rowData.amount;
        $scope.voucher_details.id = rowData.id;

        if (rowData.subAccountsId === "" || rowData.subAccountsId === undefined) {
            $scope.isSubAccHide = true;
            $scope.particularsDivClassVar = "col-xs-12 col-md-6";
            $scope.voucher_details.subAccountsId = $scope.subaccountList[0];
        } else {
            $scope.getSubAccountList(rowData.chartOfAccountsSource, rowData.chartOfAccountsId);
            $scope.particularsDivClassVar = "col-xs-12 col-md-3";
            $scope.isSubAccHide = false;
            $scope.voucher_details.subAccountsId = rowData.subAccountsId;
        }

    };

    $scope.editToGrid = function () {

        $scope.detailsAddBtnHide = false;
        $scope.detailsEditBtnHide = true;
        var details = [];

        details.chartOfAccountsId = $scope.voucher_details.chartOfAccountsId;
        details.chartOfAccountsCodeName = $scope.dropDownSelectText("chartOfAccountsId");

        if ($scope.voucher_details.subAccountsId != "" && $scope.voucher_details.subAccountsId != undefined) {
            details.subAccountsId = $scope.voucher_details.subAccountsId;
            details.subAccountsCodeName = $scope.dropDownSelectText("subAccountsId");
        }

        details.particulars = $scope.voucher_details.particulars;
        details.amount = $scope.voucher_details.amount;
        details.primeAmount = (-1) * $scope.voucher_details.amount * $scope.voucher_details.currencyRate;
        details.baseAmount = (-1) * $scope.voucher_details.amount;
        details.id = $scope.voucher_details.id;
        $scope.voucher_details_list[$scope.rowIndex] = details;


        $scope.voucherDetailsUpdate(details);

        $scope.firstRowAmountUpdate();

        $scope.amountInWords(details);

        $scope.clearTaxBox();
    };

    $scope.voucherDetailsUpdate = function (details) {
        var detailsObj;

        if(details.id === undefined){
            detailsObj = $scope.module.details[$scope.rowIndex];
        } else {
            detailsObj = $scope.module.details.find(function (v) {
                return v.id == $scope.voucher_details.id;
            });
        }

        detailsObj.chartOfAccountsId = details.chartOfAccountsId;
        detailsObj.chartOfAccountsCodeName = details.chartOfAccountsCodeName;
        detailsObj.particulars = details.particulars;
        if (details.subAccountsId != "" && details.subAccountsId != undefined) {
            detailsObj.subAccountsId = details.subAccountsId;
            detailsObj.subAccountsCodeName = details.subAccountsCodeName;
        }
        detailsObj.particulars = details.particulars;
        detailsObj.amount = details.amount;
        detailsObj.primeAmount = details.primeAmount;
        detailsObj.baseAmount = details.baseAmount;

        $scope.module.details[$scope.rowIndex] = detailsObj
    };

    $scope.firstRowAmountUpdate = function () {
        var voucherAmount = $scope.voucherAmount();
        var voucherDetailFirstRowData = $scope.module.details[0];
        voucherDetailFirstRowData.amount = voucherAmount;
        voucherDetailFirstRowData.primeAmount = (-1) * voucherAmount * $scope.voucher_details.currencyRate;
        voucherDetailFirstRowData.baseAmount = (-1) * voucherAmount;

        $scope.module.details[0] = voucherDetailFirstRowData;
    };

    $scope.deleteRow = function (index) {
        $scope.voucher_details_list.splice(index, 1);

        $scope.module.details.splice(index, 1);
        $scope.firstRowAmountUpdate();
        $scope.amountInWords();

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
        $scope.isBankHide = true;
        $scope.isSubAccHide = true;
        $scope.module.particulars = "";
        $scope.module.chequeNo = "";
        $scope.module.chequeDate = "";
        $('#amountInWords').val("");
        $('table tfoot td').eq($scope.table_debit_index).text(0);
        $('table tfoot td').eq($scope.table_credit_index).text(0);
        $scope.particularsDivClassVar = "col-xs-12 col-md-6";

        $scope.getCoaList();
        $scope.resetTable();
    };

    $scope.resetForm = function () {
        $scope.module.paymentType = VOUCHER_KEY.PAYMENT_TYPE.CASH;
        $scope.reset();
    };



});