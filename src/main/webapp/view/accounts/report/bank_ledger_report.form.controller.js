app.controller('AccReportBankLedgerFormCtrl', function ($scope, $http, $state, $timeout, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService, DialogBox, Communication, growl) {
    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;

    $scope.bankList = [];

    $scope.search = {
        fromDate: "",
        toDate: "",
        bankAccountId:"",
        accountsUsage: VOUCHER_KEY.ACCOUNTS_USAGE.BANK,
        accountsSource: VOUCHER_KEY.ACCOUNTS_SOURCE.NONE,
        chartOfAccountsId: ""
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

    $scope.selectBankAccount = function () {
        $scope.getCashBankCoa();
    };

    $scope.getCashBankCoa = function () {
        var req = Communication.request("GET", API.ACC_CHART_OF_ACCOUNTS_BY_USAGES_TYPE + '/' + $scope.search.accountsUsage);
        req.then(function (resp) {
            log("Chart of accounts by usage type: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.search.chartOfAccountsId = resp.body.id;
            }

        }, function (err) {
            log("Chart of accounts by usage type error", JSON.stringify(err));
        });
    };

    $scope.formFieldValidation = function () {
        var fromDate = new Date($scope.search.fromDate);
        var toDate = new Date($scope.search.toDate);

        if( fromDate > toDate ){
            growl.error('To date should be greater than or equal from date!', {title: 'Error!'});
            return;
        }

        if ($scope.search.fromDate === '') {
            growl.error('From date required', {title: 'Error!'});
            return false;
        }

        if ($scope.search.toDate === '') {
            growl.error('To date required', {title: 'Error!'});
            return false;
        }

        if ($scope.search.bankAccountId === '') {
            growl.error('Please select bank a/c', {title: 'Error!'});
            return false;
        }

        if ($scope.search.chartOfAccountsId === '') {
            growl.error('Bank chart of accounts not found', {title: 'Error!'});
            return false;
        }

        return true;
    };

    $scope.bankLedgerPreview = function () {
        if(!$scope.formFieldValidation()){
            return;
        }
        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", API.ACC_REPORT_BANK_LEDGER + '/' + $scope.search.fromDate + '/' + $scope.search.toDate + '/' + $scope.search.chartOfAccountsId + '/' + $scope.search.bankAccountId + '/' + $scope.search.accountsUsage + '/' + $scope.search.accountsSource, true);
        xhttp.setRequestHeader('x-aip-token', _shskr_);
        xhttp.responseType = 'blob';
        xhttp.onload = function (e) {
            if (this.status === 200) {
                var pdfResponse = new Blob([this.response], {type: 'application/pdf'});
                var fileURL = URL.createObjectURL(pdfResponse);
                var link = document.createElement('a');
                link.href = fileURL;
                link.target = '_blank';
                link.click();
            }
        };
        xhttp.send();
    };

    $scope.reset = function () {
        $scope.bankList = [];
    };

});