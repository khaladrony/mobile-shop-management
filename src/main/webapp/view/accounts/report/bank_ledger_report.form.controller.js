app.controller('AccReportBankLedgerFormCtrl', function (
            $scope, $http, $state, $timeout, $stateParams, $rootScope, $sce,
            $mdDialog, $interval, ClientService, DialogBox, Communication, growl,
            DateHelperService, ReportPreviewService
            ) {
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

        const result = DateHelperService.validateAndFormat (
                        $scope.search.fromDate,
                        $scope.search.toDate
                        );
        if (!result) return;

        const fromDate = DateHelperService.formatDateLocal($scope.search.fromDate);
        const toDate = DateHelperService.formatDateLocal($scope.search.toDate);

        var reportUrl = API.ACC_REPORT_BANK_LEDGER
                        + '/' + fromDate
                        + '/' + toDate
                        + '/' + $scope.search.chartOfAccountsId
                        + '/' + $scope.search.bankAccountId
                        + '/' + $scope.search.accountsUsage
                        + '/' + $scope.search.accountsSource;
        var token = _shskr_;

        ReportPreviewService.previewPdf(reportUrl, token)
            .then(function () {
                console.log("PDF opened successfully.");
            })
            .catch(function (error) {
                console.error(error);
            });
    };

    $scope.reset = function () {
        $scope.bankList = [];
    };
});