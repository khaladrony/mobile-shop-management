app.controller('AccVoucherPostingListCtrl', function (
            $scope, $http, $state, $timeout, $rootScope, $mdDialog, DialogBox,
            $interval, Communication, growl, AccountsService
            ) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.voucher_status = VOUCHER_KEY.STATUS.DRAFT;
    $scope.list = [];

    $scope.search = {
        from_date: "",
        to_date: "",
        voucher_no: "",
        payment_type: "",
        status: VOUCHER_KEY.STATUS.DRAFT,
        voucher_type: null
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
        const api = API.ACC_VOUCHER_POSTING_FILTER;
        AccountsService.getFilteredVouchers(api, $scope.search, currentPage, itemPerPage)
            .then(function (data) {
                $scope.currentPage = currentPage;
                $scope.data = data;
            }, function (err) {
                if (typeof err === 'string') {
                    $rootScope.toastError(err);
                } else {
                    log("Posting voucher error", JSON.stringify(err));
                }
            });
    };

    $scope.toggleDetails = function (master) {
        master.showDetails = !master.showDetails;

        if (master.showDetails && !master.details) {
            AccountsService.getVoucherDetails(master.id).then(function (details) {
                master.details = details;

                var totals = AccountsService.calculateVoucherTotals(details);
                master.debitTotal = totals.debitTotal;
                master.creditTotal = totals.creditTotal;
            });
        }
    };

    $scope.getVoucherSuggestions = function (query) {
        return AccountsService.getVoucherNoWithStatus(
                $scope.search.voucher_type,
                $scope.search.status,
                query
                );
    };

    $scope.doFilter = function (currentPage, itemPerPage) {
        $scope.getDataList(currentPage, itemPerPage);
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

        if (ids.size === 0) {
            growl.error('No voucher selected!',{title: 'Error!'});
            return;
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
        AccountsService.previewVoucher(id);
    };
});