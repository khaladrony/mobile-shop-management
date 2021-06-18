app.controller('AccVoucherPostingListCtrl', function ($scope, $http, $state, $timeout, $rootScope, $mdDialog, DialogBox, $interval, Communication,growl) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.voucher_status = VOUCHER_KEY.STATUS.DRAFT;
    $scope.list = [];

    $scope.search = {
        from_date: "",
        to_date: "",
        voucher_no: "",
        payment_type: "",
        status: VOUCHER_KEY.STATUS.DRAFT,
        voucher_type: ""
    };

    //filtering table column
    $scope.orderByField = '';
    $scope.reverseSort = false;

    //pagination variables
    $scope.data = {};
    $scope.data.items = [];
    $scope.data.itemCount = 0;
    $scope.currentPage = 1;
    $scope.itemPerPage = 10;

    $scope.getDataList = function (currentPage, itemPerPage) {

        var fromDate = new Date($scope.search.from_date);
        var toDate = new Date($scope.search.to_date);

        if( fromDate > toDate ){
            $rootScope.toastError("To date should be greater than or equal from date!");
            return;
        }

        $scope.currentPage = currentPage;
        $scope.data = {};
        $scope.data.items = [];
        $scope.data.itemCount = 0;

        DialogBox.showProgress();
        var req = Communication.request("POST", API.ACC_VOUCHER_POSTING_FILTER + "/" + currentPage + "/" + itemPerPage, $scope.search);
        req.then(function (resp) {
            DialogBox.hideProgress();
            log("Debit voucher list: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.data = resp.body;
                $scope.getVoucherStatusList();
            }

        }, function (err) {
            log("Debit voucher error", JSON.stringify(err));
        });
    };

    $scope.getVoucherStatusList = function () {

        $scope.voucherStatusList = [];

        var req = Communication.request("GET", API.ACC_VOUCHER_STATUS_LIST, {});
        req.then(function (resp) {
            log("voucher status list: " + JSON.stringify(resp));
            if (resp.code === 200) {
                $scope.voucherStatusList = resp.body;
            }
        }, function (err) {
            log("voucher status list error", JSON.stringify(err));
        });
    };

    $scope.doFilter = function (currentPage, itemPerPage) {
        $scope.getDataList(currentPage, itemPerPage);
    };

    $scope.voucherCreate = function () {
        $state.go(JCOMPONENT.acc_debit_voucher_add_view);
    };

    $scope.checkAll = function () {

        if ($scope.selectAll === undefined || $scope.selectAll === false) {
            for (var i = 0; i < $scope.data.items.length; i++) {
                $scope.data.items[i].selected = true;
            }

        } else {
            for (var i = 0; i < $scope.data.items.length; i++) {
                $scope.data.items[i].selected = false;
            }
        }
    };

    $scope.voucherPost = function () {

        var ids = new Set();
        for (var i = 0; i < $scope.data.items.length; i++) {
            if ($scope.data.items[i].selected) {
                ids.add($scope.data.items[i].id);
            }
        }

        var req;

        req = Communication.request("POST", API.ACC_VOUCHER_POSTING,{"voucherIds":Array.from(ids)});
        req.then(function (resp) {
            log("voucher posting: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;
                $scope.doFilter(1, $scope.itemPerPage);
                $scope.selectAll = false;

            } else{
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            log("voucher posting error", JSON.stringify(err));
            $rootScope.toastError(err.message);
        });
    };

    $scope.voucherPreview = function (id) {

        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", API.ACC_VOUCHER_REPORT_VIEW + '/' + id, true);
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

});