app.controller('AccChartOfAccountsFormCtrl', function ($scope, $http, $state, $timeout, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService, DialogBox, Communication, $window) {
    $rootScope.setPageName(JMODULE_NAME, $state.current.name);
    $scope.current_state = $state.current.name;

    $scope.accountsTypeList = [];
    $scope.accountsUsageList = [];
    $scope.accountsSourceList = [];

    $scope.module = {
        accountsCode: "",
        accountsName: "",
        accountsType: "Asset",
        accountsUsage: "Ledger",
        accountsSource: "None",
        active: true,
        isLeaf: true,
        createdBy: 0,
        createOn: new Date(),
        updatedBy: 0,
        updatedOn: null
    };

    $scope.init = function () {
        $scope.getAccountsTypeList();
        $scope.getAccountsUsageList();
        $scope.getAccountsSourceList();
    };

    if ($state.current.name === JCOMPONENT.acc_chart_of_accounts_update_view) {

        var req = Communication.request("GET", API.ACC_CHART_OF_ACCOUNTS_GET + '/' + $stateParams.id, $scope.module);

        req.then(function (resp) {
            log("Chart of accounts edit: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;
            }

        }, function (err) {
            log("Chart of accounts edit error", JSON.stringify(err));
        });
    }


    $scope.saveChartOfAccounts = function () {
        var req;

        if ($state.current.name === JCOMPONENT.acc_chart_of_accounts_update_view) {
            req = Communication.request("PUT", API.ACC_CHART_OF_ACCOUNTS_UPDATE, $scope.module);
        } else {
            req = Communication.request("POST", API.ACC_CHART_OF_ACCOUNTS_SAVE, $scope.module);
        }

        req.then(function (resp) {
            log("Chart of accounts add: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;
                $state.go(JCOMPONENT.acc_chart_of_accounts_list_view);

                $rootScope.toastSuccess("Successfully saved");
                $scope.module = {
                    accountsType: "Asset",
                    accountsUsage: "Ledger",
                    accountsSource: "None"
                };
            } else {
                $rootScope.toastWarning(resp.message);
            }

        }, function (err) {
            log("Chart of accounts error", JSON.stringify(err));
        });
    };

    $scope.getAccountsTypeList = function () {
        $scope.accountsTypeList = [
            {'id': 'Asset', 'value': 'Asset'},
            {'id': 'Expenditure', 'value': 'Expenditure'},
            {'id': 'Income', 'value': 'Income'},
            {'id': 'Liability', 'value': 'Liability'}
        ];
    };

    $scope.getAccountsUsageList = function () {
        $scope.accountsUsageList = [
            {'id': 'Ledger', 'value': 'Ledger'},
            {'id': 'Bank', 'value': 'Bank'},
            {'id': 'Cash', 'value': 'Cash'},
            {'id': 'AP', 'value': 'AP'},
            {'id': 'AR', 'value': 'AR'}
        ];
    };

    $scope.getAccountsSourceList = function () {
        $scope.accountsSourceList = [
            {'id': 'None', 'value': 'None'},
            {'id': 'Subaccount', 'value': 'Subaccount'},
            {'id': 'Bank', 'value': 'Bank'},
            {'id': 'Customer', 'value': 'Customer'},
            {'id': 'Employee', 'value': 'Employee'},
            {'id': 'Supplier', 'value': 'Supplier'}
        ];
    };

    $scope.accountsTypeChange = function (accountsType) {
        $scope.module.accountsType = accountsType;
    };
    $scope.accountsUsageChange = function (accountsUsage) {
        $scope.module.accountsUsage = accountsUsage;
    };
    $scope.accountsSourceChange = function (accountsSource) {
        $scope.module.accountsSource = accountsSource;
    };



});