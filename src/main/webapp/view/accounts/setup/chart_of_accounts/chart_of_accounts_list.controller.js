app.controller('AccChartOfAccountsListCtrl', function ($scope, $http, $state, $timeout, $rootScope, $mdDialog, DialogBox, $interval, Communication) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.list = [];

    $scope.search = {};
    //filtering table column
    $scope.orderByField = '';
    $scope.reverseSort = false;

    //pagination variables
    $scope.data = {};
    $scope.data.items = [];
    $scope.data.itemCount = 0;
    $scope.currentPage = 1;
    $scope.itemPerPage = 50;

    $scope.getDataList = function (currentPage, itemPerPage) {

        $scope.currentPage = currentPage;
        $scope.data = {};
        $scope.data.items = [];
        $scope.data.itemCount = 0;

        DialogBox.showProgress();
        var req = Communication.request("POST", API.ACC_CHART_OF_ACCOUNTS_FILTER + "/" + currentPage + "/" + itemPerPage, $scope.search);
        req.then(function (resp) {
            DialogBox.hideProgress();
            log("chart of accounts voucher list: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.data = resp.body;
            }

        }, function (err) {
            log("chart of accounts error", JSON.stringify(err));
        });
    };

    $scope.doFilter = function (currentPage, itemPerPage) {
        $scope.getDataList(currentPage, itemPerPage);
    };

    $scope.showEditForm = function (obj) {
        $state.go(JCOMPONENT.acc_chart_of_accounts_update_view, {id: obj.id});
    };

    $scope.reportView = function () {
        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", API.ACC_REPORT_CHART_OF_ACCOUNTS_VIEW, true);
        xhttp.setRequestHeader('x-aip-token', _shskr_);
        xhttp.responseType = 'blob';
        xhttp.onload = function (e) {
            if (this.status === 200) {
                var campaign = new Blob([this.response], {type: 'application/pdf'});

                var fileURL = URL.createObjectURL(campaign);
                var link = document.createElement('a');
                link.href = fileURL;
                link.target = '_blank';
                link.click();
            }
        };
        xhttp.send();
    };

});