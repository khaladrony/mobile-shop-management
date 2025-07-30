app.controller('InventoryMovementListCtrl', function ($scope, $http, $state, $timeout,
                $rootScope, $mdDialog, DialogBox, $interval, Communication, growl, ItemService,
                ToasterMessageQueueService, DateHelperService, InventoryService, ToastService) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.receive_status = INVENTORY_KEY.STATUS.OPEN;
    $scope.list = [];
    $scope.itemList = [];

    $scope.search = {
        fromDate: "",
        toDate: "",
        transactionId: "",
        status: INVENTORY_KEY.STATUS.OPEN,
        action: INVENTORY_KEY.ACTION.RECEIPT,
        sortField: "transactionDate",
        sortDirection: "desc"
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
            log("Inventory movement receipt error", JSON.stringify(err));
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

    $scope.showEditForm = function (obj) {
        $state.go(JCOMPONENT.inventory_movement_update_view, {id: obj.id});
    };

    $scope.preview = function (id) {
        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", API.INVENTORY_MOVEMENT_REPORT_VIEW + '/' + id, true);
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