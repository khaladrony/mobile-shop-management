app.controller('AccReportAccountLedgerFormCtrl', function (
            $scope, $http, $state, $timeout, $stateParams, $rootScope, $sce,
            $mdDialog, $interval, ClientService, DialogBox, Communication, growl,
            DateHelperService, ReportPreviewService
            ) {
    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;
    
    $scope.search = {
        fromDate: "",
        toDate: "",
        chartOfAccountsId: "",
        chartOfAccountsCodeName: ""
    };

    $scope.getCoaList = function () {
        var req = Communication.request("GET", API.ACC_CHART_OF_ACCOUNTS_LIST_DROP_DOWN, {});
        req.then(function (resp) {
            log("Chart of accounts list: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.coaList = resp.body;
            }

        }, function (err) {
            log("Chart of accounts error", JSON.stringify(err));
        });
    };

    $scope.selectChartOfAccounts = function () {

        $scope.search.chartOfAccountsCodeName = $scope.dropDownSelectText("chartOfAccountsId");
    };

    $scope.dropDownSelectText = function (elementid) {
        var s1 = document.getElementById(elementid);
        var text = s1.options[s1.selectedIndex].text.substring(0);
        return text;
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

        if ($scope.search.chartOfAccountsId === '') {
            growl.error('Please select chart of accounts', {title: 'Error!'});
            return false;
        }

        return true;
    };

    $scope.accountLedgerPreview = function () {
        if(!$scope.formFieldValidation()){
            return;
        }

        const result = DateHelperService.validateAndFormat(
                        $scope.search.fromDate,
                        $scope.search.toDate
                        );
        if (!result) return;

        const fromDate = DateHelperService.formatDateLocal($scope.search.fromDate);
        const toDate = DateHelperService.formatDateLocal($scope.search.toDate);

        const encodedAccountName = encodeURIComponent($scope.search.chartOfAccountsCodeName);
        var reportUrl = API.ACC_REPORT_ACCOUNT_WISE_LEDGER
                        + '/' + fromDate
                        + '/' + toDate
                        + '/' + $scope.search.chartOfAccountsId
                        + '/' + encodedAccountName;
        var token = _shskr_;

        ReportPreviewService.previewPdf(reportUrl, token)
            .then(function () {
                console.log("PDF opened successfully.");
            })
            .catch(function (error) {
                console.error(error);
            });
    };
});