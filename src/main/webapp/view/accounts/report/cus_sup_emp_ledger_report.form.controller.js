app.controller('AccReportCusSupEmpLedgerFormCtrl', function (
            $scope, $http, $state, $timeout, $stateParams, $rootScope, $sce,
            $mdDialog, $interval, ClientService, DialogBox, Communication, growl,
            DateHelperService, ReportPreviewService
            ) {
    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;

    $scope.search = {
        fromDate: "",
        toDate: "",
        accountsSource: "",
        subAccountsId:""
    };

    $scope.selectAccountsSource = function (accountsSource) {
        $scope.reset();

        $scope.getSubAccountList(accountsSource, 0);
    };

    $scope.getSubAccountList = function (accountsSource, chartOfAccountsId) {

        var req = Communication.request("GET", API.ACC_GET_SUB_ACCOUNTS_LIST + '/' + accountsSource + '/' + chartOfAccountsId, {});
        req.then(function (resp) {
            log("Sub account list: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.subaccountList = resp.body;
            }

        }, function (err) {
            log("Sub account list error", JSON.stringify(err));
        });
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

        if ($scope.search.accountsSource === '') {
            growl.error('Please select sub accounts type', {title: 'Error!'});
            return false;
        }

        if ($scope.search.subAccountsId === '') {
            growl.error('Please select sub accounts', {title: 'Error!'});
            return false;
        }

        return true;
    };

    $scope.subAccountLedgerPreview = function () {
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

        var reportUrl = API.ACC_REPORT_CUS_SUP_EMP_WISE_LEDGER
                        + '/' + fromDate
                        + '/' + toDate
                        + '/' + $scope.search.subAccountsId
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
        $scope.subaccountList = [];
        $scope.getSubAccountList();
    };
});