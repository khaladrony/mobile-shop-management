app.controller('InventoryMovementListCtrl', function ($scope, $http, $state, $timeout,
                $rootScope, $mdDialog, DialogBox, $interval, Communication,growl) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.receive_status = INVENTORY_KEY.STATUS.OPEN;
    $scope.list = [];
    $scope.itemList = [];

    $scope.search = {
        fromDate: "",
        toDate: "",
        transactionId: "",
        status: INVENTORY_KEY.STATUS.OPEN,
        action: INVENTORY_KEY.ACTION.RECEIPT
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

        $scope.search.fromDate = $scope.fromDate
                         ? formatToLocalDateTimeString($scope.fromDate)
                         : "";
        $scope.search.toDate = $scope.toDate
                       ? formatToLocalDateTimeString($scope.toDate)
                       : "";

        if( $scope.search.fromDate  > $scope.search.toDate ){
            growl.error('To date should be greater than or equal from date!',{title: 'Error!'});
            return;
        }

        $scope.currentPage = currentPage;
        $scope.data = {};
        $scope.data.items = [];
        $scope.data.itemCount = 0;

        var url = API.INVENTORY_MOVEMENT_FILTER + '?page=' + (currentPage - 1) + '&size=' + itemPerPage;

        DialogBox.showProgress();
        var req = Communication.request("POST", url, $scope.search);
        req.then(function (resp) {
            DialogBox.hideProgress();
            log("Inventory movement receipt list: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.data.items = resp.body.content;
                $scope.data.itemCount = resp.body.totalElements;

                $scope.getItemList();

                $scope.data.items.forEach(function (master) {
                    master.showDetails = false;
                });
            }

        }, function (err) {
            log("Inventory movement receipt error", JSON.stringify(err));
        });
    };

    $scope.getItemList = function () {
            var req = Communication.request("GET", API.ITEM_MASTER_ITEMS_DROP_DOWN, {});
            req.then(function (resp) {
                log("Item list: " + JSON.stringify(resp));

                if (resp.code === 200) {
                   $scope.itemList = resp.body
                }
            }, function (err) {
                log("Item list fetch error", JSON.stringify(err));
            });
        };

    $scope.toggleDetails = function (master) {
        master.showDetails = !master.showDetails;
        master.details.forEach(function (detail) {
            detail.itemNameCode = $scope.itemList.find(
                        item => item.item_code === detail.itemCode).item_name_code;
        });

        $scope.getItemDetailsTotalQty = function(master) {
            return master.details.reduce(function(total, row) {
                return total + (parseFloat(row.quantity) || 0);
            }, 0);
        };
    };

    function formatToLocalDateTimeString(date) {
        const yyyy = date.getFullYear();
        const mm = String(date.getMonth() + 1).padStart(2, '0');
        const dd = String(date.getDate()).padStart(2, '0');
        return `${yyyy}-${mm}-${dd}T00:00:00`;
    }

    $scope.getTransactionSuggestions = function (query) {
        console.log("Query:", query);
        return $http.get('/inventory/inventory-movement/search-transaction-id?q=' + query).then(function (resp) {
            console.log("Suggestions:", resp.data.body);
            return resp.data.body;
        });
    };

    $scope.doFilter = function (currentPage, itemPerPage) {
        $scope.getDataList(currentPage, itemPerPage);
    };

    $scope.showEditForm = function (obj) {
        $state.go(JCOMPONENT.inventory_movement_update_view, {id: obj.id});
    };

    $scope.voucherCreate = function () {
        $state.go(JCOMPONENT.inventory_movement_add_view);
    };
});