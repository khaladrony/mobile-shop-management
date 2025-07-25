app.controller('InventoryMovementPostingListCtrl', function ($scope, $http, $state, $timeout,
                 $rootScope, $mdDialog, DialogBox, $interval, Communication, growl, ItemService,
                 ToasterMessageQueueService, DateHelperService, InventoryService, ToastService) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.list = [];

    $scope.search = {
        fromDate: "",
        toDate: "",
        transactionId: "",
        status: INVENTORY_KEY.STATUS.OPEN,
        sortField: "transactionDate",
        sortDirection: "desc",
        action: null
    };

    $scope.fromDate = '';
    $scope.toDate = '';
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

        const result = DateHelperService.validateAndFormat($scope.fromDate, $scope.toDate);

        if (!result) return;

        $scope.search.fromDate = result.fromDate;
        $scope.search.toDate = result.toDate;

        $scope.currentPage = currentPage;
        $scope.data = {};
        $scope.data.items = [];
        $scope.data.itemCount = 0;

        var url = API.INVENTORY_MOVEMENT_FILTER + '?page=' + (currentPage - 1) + '&size=' + itemPerPage;

        DialogBox.showProgress();
        var req = Communication.request("POST", url, $scope.search);
        req.then(function (resp) {
            DialogBox.hideProgress();

            if (resp.code === 200) {
                $scope.data.items = resp.body.content;
                $scope.data.itemCount = resp.body.totalElements;

                $scope.getItemList();

                $scope.data.items.forEach(function (master) {
                    master.showDetails = false;
                });
                ToastService.showMessages();
            }

        }, function (err) {
            log("Inventory movement list fetch error", JSON.stringify(err));
        });
    };

    $scope.getItemList = function () {
        return ItemService.getItemList()
            .then(function (items) {
                $scope.itemList = items;
           }).catch(function (err) {
                log("Item list error", err);
           }).finally(function () {
           });
    };

    $scope.toggleDetails = function (master) {
        master.showDetails = !master.showDetails;
        master.details.forEach(function (detail) {
            detail.itemNameCode = $scope.itemList.find(
                        item => item.item_code === detail?.itemCode
                    )?.item_name_code || null;
        });

        $scope.getItemDetailsTotalQty = function(master) {
            return master.details.reduce(function(total, row) {
                return total + (parseFloat(row.quantity) || 0);
            }, 0);
        };
    };

    $scope.getTransactionSuggestions = function (query) {
        return InventoryService.getTransactionId($scope.search.action, query);
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

    $scope.inventoryMovementPost = function () {
        var ids = new Set();
        for (var i = 0; i < $scope.data.items.length; i++) {
            if ($scope.data.items[i].selected) {
                ids.add($scope.data.items[i].id);
            }
        }

        var req;

        req = Communication.request("POST", API.INVENTORY_MOVEMENT_POSTING,{"inventoryMovementIds":Array.from(ids)});
        req.then(function (resp) {
            log("Inventory posting: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;
                $scope.doFilter(1, $scope.itemPerPage);
                $scope.selectAll = false;

            } else{
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            log("Inventory posting error", JSON.stringify(err));
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

    $scope.sortBy = function(field) {
        if ($scope.orderByField === field) {
            $scope.reverseSort = !$scope.reverseSort; // toggle direction
        } else {
            $scope.orderByField = field;
            $scope.reverseSort = false;
        }
    };

});