app.controller('AccCreditVoucherListCtrl', function ($scope, $http, $state, $timeout, $rootScope, $mdDialog, DialogBox, $interval, Communication,growl) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.voucher_status = VOUCHER_KEY.STATUS.DRAFT;
    $scope.list = [];

    $scope.search = {
        from_date: "",
        to_date: "",
        voucher_no: "",
        payment_type: "",
        status: "",
        voucher_type: VOUCHER_KEY.TYPE.CREDIT_VOUCHER
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
            growl.warning('To date should be greater than or equal from date!',{title: 'Warning!'});
            return;
        }

        $scope.currentPage = currentPage;
        $scope.data = {};
        $scope.data.items = [];
        $scope.data.itemCount = 0;

        DialogBox.showProgress();
        var req = Communication.request("POST", API.ACC_CREDIT_VOUCHER_FILTER + "/" + currentPage + "/" + itemPerPage, $scope.search);
        req.then(function (resp) {
            DialogBox.hideProgress();
            log("Credit voucher list: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.data = resp.body;
            }

        }, function (err) {
            log("Credit voucher error", JSON.stringify(err));
        });
    };

    $scope.getVoucherNoList = function () {

        $scope.voucherNoList = [];
        var req = Communication.request("GET", API.ACC_CREDIT_VOUCHER_LIST + "/" + $scope.search.voucher_type, {});
        req.then(function (resp) {
            log("Credit voucher no list: " + JSON.stringify(resp));
            if (resp.code === 200) {
                $scope.voucherNoList = resp.body;
                $scope.getVoucherStatusList();
            }
        }, function (err) {
            log("Credit voucher no list error", JSON.stringify(err));
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

    $scope.showEditForm = function (obj) {
        $state.go(JCOMPONENT.acc_credit_voucher_update_view, {id: obj.id});
    };

    $scope.voucherCreate = function () {
        $state.go(JCOMPONENT.acc_credit_voucher_add_view);
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