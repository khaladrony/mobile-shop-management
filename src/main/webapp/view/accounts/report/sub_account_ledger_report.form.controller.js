app.controller('AccReportSubAccountLedgerFormCtrl', function ($scope, $http, $state, $timeout, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService, DialogBox, Communication, growl) {
    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;

    $scope.search = {
        fromDate: "",
        toDate: "",
        chartOfAccountsId: "",
        chartOfAccountsCodeName: "",
        accountsSource: "",
        subAccountsId:""
    };

    $scope.getCoaList = function () {
        var req = Communication.request("GET", API.ACC_CHART_OF_ACCOUNTS_LIST_DROP_DOWN, {});
        req.then(function (resp) {
            log("Chart of accounts list: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.coaList = resp.body.filter((it) => it.accounts_source === 'Subaccount');
            }

        }, function (err) {
            log("Chart of accounts error", JSON.stringify(err));
        });
    };

    $scope.selectChartOfAccounts = function (coaId) {
        $scope.reset();

        $scope.search.chartOfAccountsCodeName = $scope.dropDownSelectText("chartOfAccountsId");

        var chartOfAccountsObj = $scope.coaList.filter(item => item.id === coaId);
        $scope.search.accountsSource = chartOfAccountsObj[0].accounts_source;
        $scope.getSubAccountList($scope.search.accountsSource, coaId);
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

        if ($scope.search.chartOfAccountsId === '') {
            growl.error('Please select chart of accounts', {title: 'Error!'});
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

        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", API.ACC_REPORT_SUB_ACCOUNT_WISE_LEDGER + '/' + $scope.search.fromDate + '/' + $scope.search.toDate + '/' + $scope.search.chartOfAccountsId + '/' + $scope.search.chartOfAccountsCodeName + '/' + $scope.search.subAccountsId + '/' + $scope.search.accountsSource, true);
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
        $scope.subaccountList = [];
        $scope.getSubAccountList();
    };

});