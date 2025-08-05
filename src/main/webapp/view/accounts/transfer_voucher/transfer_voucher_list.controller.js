app.controller('AccTransferVoucherListCtrl', function (
            $scope, $http, $state, $timeout, $rootScope, $mdDialog, $interval,
            DialogBox, Communication, growl, AccountsService
            ) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.voucher_status = VOUCHER_KEY.STATUS.DRAFT;
    $scope.list = [];

    $scope.search = {
        from_date: "",
        to_date: "",
        voucher_no: "",
        payment_type: "",
        status: null,
        voucher_type: VOUCHER_KEY.TYPE.TRANSFER_VOUCHER
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
        const api = API.ACC_TRANSFER_VOUCHER_FILTER;
        AccountsService.getFilteredVouchers(api, $scope.search, currentPage, itemPerPage)
            .then(function (data) {
                $scope.currentPage = currentPage;
                $scope.data = data;
            }, function (err) {
                if (typeof err === 'string') {
                    $rootScope.toastError(err);
                } else {
                    log("Debit voucher error", JSON.stringify(err));
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
        return AccountsService.getVoucherNo($scope.search.voucher_type, query);
    };

    $scope.doFilter = function (currentPage, itemPerPage) {
        $scope.getDataList(currentPage, itemPerPage);
    };

    $scope.showEditForm = function (obj) {
        $state.go(JCOMPONENT.acc_transfer_voucher_update_view, {id: obj.id});
    };

    $scope.voucherCreate = function () {
        $state.go(JCOMPONENT.acc_transfer_voucher_add_view);
    };

    $scope.voucherPreview = function (id) {
        AccountsService.previewVoucher(id);
    };
});