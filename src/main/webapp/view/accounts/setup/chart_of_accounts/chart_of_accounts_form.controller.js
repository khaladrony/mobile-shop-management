app.controller('AccChartOfAccountsFormCtrl', function (
            $scope, $http, $state, $timeout, $stateParams, $rootScope, $sce,
            $mdDialog, $interval, ClientService, DialogBox, Communication,
            $window, growl, AccountsService
            ) {
    $rootScope.setPageName(JMODULE_NAME, $state.current.name);
    $scope.current_state = $state.current.name;

    $scope.data = {};
    $scope.data.items = [];
    $scope.data.itemCount = 0;
    $scope.currentPage = 1;
    $scope.itemPerPage = 1000;
    $scope.search = {};

    $scope.accountsTypeList = [];
    $scope.accountsUsageList = [];
    $scope.accountsSourceList = [];
    $scope.accountGroup = null;

    $scope.defaultModule = {
        accountsCode: "",
        accountsName: "",
        accountsType: "Asset",
        accountsUsage: "Ledger",
        accountsSource: "None",
        masterType: "Balance Sheet",
        group1: "",
        active: true,
        isLeaf: true,
        createdBy: 0,
        createOn: new Date(),
        updatedBy: 0,
        updatedOn: null
    };

    $scope.init = function () {
        $scope.module = angular.copy($scope.defaultModule);
        $scope.getDataList($scope.currentPage, $scope.itemPerPage);
    };

    $scope.getDataList = function (currentPage, itemPerPage) {
        $scope.currentPage = currentPage;
        $scope.data = {};
        $scope.data.items = [];
        $scope.data.itemCount = 0;

        DialogBox.showProgress();
        var req = Communication.request("POST", API.ACC_CHART_OF_ACCOUNTS_FILTER + "/" + currentPage + "/" + itemPerPage, $scope.search);
        req.then(function (resp) {
            DialogBox.hideProgress();

            if (resp.code === 200) {
                $scope.coaTree = $scope.buildAccountTypeTree(resp.body.items);
            }
        }, function (err) {
            log("chart of accounts error", JSON.stringify(err));
        });
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

        if (['Income', 'Expenditure'].includes($scope.module.accountsType )) {
            $scope.module.masterType = "Revenue";
        } else {
            $scope.module.masterType = "Balance Sheet";
        }

        $scope.module.group1 = $scope.accountGroup ? $scope.accountGroup.xcode : "";

        if ($state.current.name === JCOMPONENT.acc_chart_of_accounts_update_view) {
            req = Communication.request("PUT", API.ACC_CHART_OF_ACCOUNTS_UPDATE, $scope.module);
        } else {
            req = Communication.request("POST", API.ACC_CHART_OF_ACCOUNTS_SAVE, $scope.module);
        }

        req.then(function (resp) {
            log("Chart of accounts add: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;

                growl.success('Successfully saved',{title: 'Success!'});
                $scope.resetModule();
                $scope.getDataList($scope.currentPage, $scope.itemPerPage);
            } else {
                $rootScope.toastWarning(resp.message);
            }

        }, function (err) {
            log("Chart of accounts error", JSON.stringify(err));
        });
    };

    $scope.accountsTypeList = AccountsService.getAccountsTypeList();
    $scope.accountsUsageList = AccountsService.getAccountsUsageList();
    $scope.accountsSourceList = AccountsService.getAccountsSourceList();

    $scope.accountsTypeChange = function (accountsType) {
        $scope.module.accountsType = accountsType;
        $scope.module.group1 = null;
    };
    $scope.accountsUsageChange = function (accountsUsage) {
        $scope.module.accountsUsage = accountsUsage;
    };
    $scope.accountsSourceChange = function (accountsSource) {
        $scope.module.accountsSource = accountsSource;
    };

    $scope.buildAccountTypeTree = function (flatAccounts) {
        const typeGroup = {};

        flatAccounts.forEach(item => {
            const type = item.accounts_type;
            const accountGroup = item.group1;

            if (!typeGroup[type]) {
                typeGroup[type] = {
                    label: type,
                    code: '',
                    collapsed: false,
                    children: {}
                };
            }

            if (!typeGroup[type].children[accountGroup]) {
                typeGroup[type].children[accountGroup] = {
                    label: accountGroup,
                    code: '',
                    collapsed: true,
                    children: []
                };
            }

            typeGroup[type].children[accountGroup].children.push({
                id: item.id,
                label: item.accounts_name,
                source: item.accounts_source,
                code: item.accounts_code,
                collapsed: false,
                children: []
            });
        });

        // Convert inner usage map to array
        const finalTree = Object.values(typeGroup).map(typeNode => {
            typeNode.children = Object.values(typeNode.children);
            return typeNode;
        });

        return finalTree;
    };

    $scope.toggle = function (node) {
        node.collapsed = !node.collapsed;
    };

    $scope.resetModule = function () {
        $scope.accountGroup = null;
        $scope.module = angular.copy($scope.defaultModule);
    };
});