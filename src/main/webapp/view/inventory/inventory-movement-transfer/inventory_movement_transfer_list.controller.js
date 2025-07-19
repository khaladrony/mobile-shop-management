app.controller('InventoryMovementTransferListCtrl', function ($scope, $http, $state, $timeout,
                $rootScope, $mdDialog, DialogBox, $interval, Communication, growl, ItemService,
                ToasterMessageQueueService, DateUtilService, InventoryService) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.receive_status = INVENTORY_KEY.STATUS.OPEN;
    $scope.list = [];
    $scope.itemList = [];

    $scope.search = {
        fromDate: "",
        toDate: "",
        transactionId: "",
        status: INVENTORY_KEY.STATUS.OPEN,
        action: INVENTORY_KEY.ACTION.TRANSFER
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
                         ? DateUtilService.formatToLocalDateTimeString($scope.fromDate)
                         : "";
        $scope.search.toDate = $scope.toDate
                       ? DateUtilService.formatToLocalDateTimeString($scope.toDate)
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
            log("Inventory movement transfer list: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.data.items = resp.body.content;
                $scope.data.itemCount = resp.body.totalElements;

                $scope.getItemList();

                $scope.data.items.forEach(function (master) {
                    master.showDetails = false;
                });
            }

        }, function (err) {
            log("Inventory movement transfer error", JSON.stringify(err));
        });
    };

    $scope.getItemList = function () {
        return ItemService.getItemList()
            .then(function (items) {
                $scope.itemList = items;
           }).catch(function (err) {
                log("Item list error", err);
           }).finally(function () {
                DialogBox.hideProgress();
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

    $scope.getTransactionSuggestions = function (query) {
        return InventoryService.getTransactionId($scope.search.action, query);
    };

    $scope.doFilter = function (currentPage, itemPerPage) {
        $scope.getDataList(currentPage, itemPerPage);
    };

    $scope.showEditForm = function (obj) {
        $state.go(JCOMPONENT.inventory_movement_transfer_update_view, {id: obj.id});
    };

    $scope.toastMessage = function(){
        var messages = ToasterMessageQueueService.getMessages();
         messages.forEach(function (msg) {
            if (msg.type === 'success') {
                growl.success(msg.message, { title: msg.title });
            } else if (msg.type === 'error') {
                growl.error(msg.message, { title: msg.title });
            }
        });
    }
});