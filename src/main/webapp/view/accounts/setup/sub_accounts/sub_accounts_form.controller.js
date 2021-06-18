app.controller('AccSubAccountsFormCtrl', function ($scope, $http, $state, $timeout,
                                                   $stateParams, $rootScope, $sce, $mdDialog,
                                                   $interval, ClientService, DialogBox, Communication) {

    $rootScope.setPageName(JMODULE_NAME, $state.current.name);
    $scope.current_state = $state.current.name;

    $scope.coaList = [];
    $scope.list = [];
    $scope.module = {
        id: null,
        chartOfAccountsId: "",
        subAccountsCode: "",
        subAccountsName: "",
        status: true,
        createdBy: 0,
        createOn: new Date()
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

    $scope.selectChartOfAccounts = function (chartOfAccountsId) {
        var sl = document.getElementById("chartOfAccountsId");
        $scope.module.chartOfAccountsCode = sl.options[sl.selectedIndex].text.substring(0, 6);

        $scope.reset('1');
        $scope.getDataList(chartOfAccountsId);
    };

    $scope.saveSubAccounts = function () {
        var req;

        if ($state.current.name === JCOMPONENT.acc_sub_accounts_update_view) {
            req = Communication.request("PUT", API.ACC_SUB_ACCOUNTS_UPDATE, $scope.module);
        } else {
            req = Communication.request("POST", API.ACC_SUB_ACCOUNTS_SAVE, $scope.module);
        }

        req.then(function (resp) {
            log("Sub accounts add: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;
                $scope.getDataList($scope.module.chartOfAccountsId);
                $scope.module.subAccountsName = "";
                $scope.module.subAccountsCode = "";
                $scope.module.id = null;
                $state.go(JCOMPONENT.acc_sub_accounts_add_view);

                $rootScope.toastSuccess("Successfully saved");

            } else {
                $rootScope.toastWarning(resp.message);
            }

        }, function (err) {
            log("Sub accounts error", JSON.stringify(err));
        });
    };

    $scope.getDataList = function (chartOfAccountsId) {
        var req = Communication.request("GET", API.ACC_SUB_ACCOUNTS_GET_ALL_BY_COA + "/" + chartOfAccountsId, {});
        req.then(function (resp) {
            log("Sub accounts list: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.list = resp.body;
            }

        }, function (err) {
            log("Sub accounts error", JSON.stringify(err));
        });
    };

    $scope.resetTable = function () {
        var table = document.getElementById("sub-account-table");

        while (table.rows.length > 1) {
            table.deleteRow(1);
        }
    };

    $scope.reset = function (val) {
        $scope.list = [];
        $scope.resetTable();
        if (val === '') {
            $scope.module = {};
        } else {
            $scope.module.subAccountsName = "";
            $scope.module.subAccountsCode = "";
        }
        $state.current.name = JCOMPONENT.acc_sub_accounts_add_view;
        $scope.current_state = $state.current.name;
    };

    $scope.showEditForm = function (obj) {

        $scope.module = obj;
        $state.current.name = JCOMPONENT.acc_sub_accounts_update_view;
        $scope.current_state = $state.current.name;
    };


});