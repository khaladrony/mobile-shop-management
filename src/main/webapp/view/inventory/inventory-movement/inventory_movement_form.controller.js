app.controller('InventoryMovementFormCtrl', function ($scope, $http, $state, $timeout,
                $q, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService,
                DialogBox, encrypt, Communication,$filter,growl) {

    copyPasteStringRestrict('.pastedString');
    $rootScope.setPageName(JMODULE_NAME, $state.current.name);
    $scope.current_state = $state.current.name;

    $scope.itemList = [];
    $scope.inventory_movement_items = [];
    $scope.item_name_code = "";

    $scope.module = {
        id:"",
        transactionId: "",
        transactionDate: new Date(),
        reference: "",
        warehouse: "",
        sign: "",
        action: INVENTORY_KEY.ACTION.RECEIPT,
        year: "",
        month: "",
        status: INVENTORY_KEY.STATUS.OPEN,
        remarks: "",
        supplierCode: "",
        customerCode: "",
        createOn: new Date(),
        details: []
    };

    $scope.inventory_movement_item = {
        id: "",
        itemCode: "",
        rate: "1",
        quantity: "",
        createOn: new Date()
    };

    $scope.itemObj = null;

    $scope.isWarehouseHide = true;
    $scope.detailsAddBtnHide = false;
    $scope.detailsEditBtnHide = true;

    /*$scope.getItemList = function () {
        var req = Communication.request("GET", API.ITEM_MASTER_ITEMS_DROP_DOWN, {});
        req.then(function (resp) {
            log("Item list: " + JSON.stringify(resp));

            if (resp.code === 200) {
               $scope.itemList = resp.body
                return $scope.itemList;
            } else {
                return [];
            }
        }, function (err) {
            log("Item list fetch error", JSON.stringify(err));
            return [];
        });
    };*/

    $scope.getItemList = function () {
        // ✅ MUST return the request promise
        return Communication.request("GET", API.ITEM_MASTER_ITEMS_DROP_DOWN, {})
            .then(function (resp) {
                log("Item list: " + JSON.stringify(resp));
                if (resp.code === 200) {
                    $scope.itemList = resp.body;
                    return $scope.itemList;
                } else {
                    return [];
                }
            }, function (err) {
                log("Item list fetch error", JSON.stringify(err));
                return [];
            });
    };

    // Item select handler
    $scope.onItemSelect = function(item) {
        console.log('Item selected:', item);
    };

    $scope.fetchItems = function(params) {
        const offset = params.page * params.limit;
        const search = params.search.toLowerCase();
        const limit = params.limit;

        return $q(function(resolve) {
            $timeout(function() {
                let filtered = $scope.itemList;
                if (search) {
                    filtered = filtered.filter(item =>
                        item.item_name_code.toLowerCase().includes(search)
                    );
                }
                const items = filtered.slice(offset, offset + limit);
                resolve({
                    items: items,
                    hasMore: offset + limit < filtered.length
                });
            }, 300);
        });
    };

    $scope.onItemChange = function(item) {
        $scope.inventory_movement_item.itemCode = item.item_code;

        $scope.selectItem($scope.inventory_movement_item.itemCode);
    };

    $scope.selectItem = function (item_code) {
        $scope.itemObj =  $scope.itemList.find(item => item.item_code === item_code);
    };

    $scope.addToGrid = function () {
        if (!$scope.gridDataValidation()) {
            return;
        }
        $scope.movementItemsObj();
        $scope.clearTaxBox();
    };

    $scope.gridDataValidation = function () {
        if ($scope.inventory_movement_item.itemCode === '') {
            growl.error('Please select item',{title: 'Error!'});
            return false;
        }

        if ($scope.inventory_movement_item.quantity === '') {
            growl.error('Please enter quantity',{title: 'Error!'});
            return false;
        }

        return true;
    };

    $scope.getTotalQuantity = function() {
        return $scope.inventory_movement_items.reduce(function(total, row) {
            return total + (parseFloat(row.quantity) || 0);
        }, 0);
    };

    $scope.movementItemsObj = function () {
        var details = [];
        details.itemCode = $scope.itemObj.item_code;
        details.rate = $scope.inventory_movement_item.rate;
        details.quantity = $scope.inventory_movement_item.quantity;
        details.item_name_code = $scope.itemObj.item_name_code;

        $scope.inventory_movement_items.push(details);

        $scope.module.details.push(angular.copy($scope.inventory_movement_item));
    };

    function formatToLocalDateTimeString(date) {
        const yyyy = date.getFullYear();
        const mm = String(date.getMonth() + 1).padStart(2, '0');
        const dd = String(date.getDate()).padStart(2, '0');
        return `${yyyy}-${mm}-${dd}T00:00:00`;
    }

    $scope.clearTaxBox = function () {
        $scope.inventory_movement_item.itemCode = "";
        $scope.inventory_movement_item.rate = "1";
        $scope.inventory_movement_item.quantity = "";
        $scope.inventory_movement_item.selectedItem = null;
    };

    $scope.rowDataPopulate = function (rowData, rowIndex) {
        $scope.detailsAddBtnHide = true;
        $scope.detailsEditBtnHide = false;

        $scope.rowIndex = rowIndex;
        $scope.inventory_movement_item.id = rowData.id;
//        $scope.inventory_movement_item.itemCode = rowData.itemCode;
        $scope.inventory_movement_item.selectedItem = $scope.itemList
                    .find(item => item.item_code === rowData.itemCode);
        $scope.inventory_movement_item.rate = rowData.rate;
        $scope.inventory_movement_item.quantity = rowData.quantity;
        $scope.selectItem(rowData.itemCode);
    };

    $scope.editToGrid = function () {
        var details = [];
        $scope.detailsAddBtnHide = false;
        $scope.detailsEditBtnHide = true;

        details.id = $scope.inventory_movement_item.id;
        details.itemCode = $scope.itemObj.item_code;
        details.rate = $scope.inventory_movement_item.rate;
        details.quantity = $scope.inventory_movement_item.quantity;
        details.item_name_code = $scope.itemObj.item_name_code;
        $scope.inventory_movement_items[$scope.rowIndex] = details;

        $scope.transactionUpdate(details);

        $scope.clearTaxBox();
    };

    $scope.saveModule = function () {
        if(!$scope.saveValidation()){
            return;
        }

        var req;
        if($state.current.name === JCOMPONENT.inventory_movement_update_view) {
            req = Communication.request("PUT", API.INVENTORY_MOVEMENT_UPDATE, $scope.module);
        } else{
          //Avoid time zone
          $scope.module.transactionDate = formatToLocalDateTimeString($scope.module.transactionDate);

          req = Communication.request("POST", API.INVENTORY_MOVEMENT_SAVE, $scope.module);
        }

        req.then(function (resp) {
            log("Inventory movement: " + JSON.stringify(resp));
            if (resp.code === 200) {
                growl.success('Successfully saved',{title: 'Success!'});
                $scope.reset();
                $scope.module.details = [];

                if($state.current.name === JCOMPONENT.inventory_movement_update_view){
                    $state.go(JCOMPONENT.inventory_movement_list_view);
                    $rootScope.toastSuccess("Successfully saved");
                }

            } else{
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            log("Inventory movement save error", JSON.stringify(err));
            $rootScope.toastError(err.message);
        });
    };

    $scope.saveValidation = function () {
        if ($scope.module.details.length === 0) {
            growl.error('Please enter item details',{title: 'Error!'});
            return false;
        }

        return true;
    };

    if($state.current.name === JCOMPONENT.inventory_movement_update_view){

        var req = Communication.request("GET", API.INVENTORY_MOVEMENT_GET + '/' + $stateParams.id, $scope.module);
        req.then(function (resp) {
            log("Receive transaction edit: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;
                //Date string convert to JS Date object
                $scope.module.transactionDate = new Date(resp.body.transactionDate);
                $scope.getGridTransactionItemsForEdit($scope.module.details);
            }
        }, function (err) {
            log("Receive transaction edit error", JSON.stringify(err));
        });
    }

    $scope.getGridTransactionItemsForEdit = function (transactionItems) {

        var details = [];
        angular.forEach(transactionItems, function (value, key) {
            $scope.selectItem(value.itemCode);
            details.id = value.id;
            details.itemCode = value.itemCode;
            details.rate = value.rate;
            details.quantity = value.quantity;
            details.item_name_code = $scope.itemObj.item_name_code;;

            $scope.inventory_movement_items.push(details);
            details = [];
        });
    };

    $scope.transactionUpdate = function (details) {
        var detailsObj;

        if(details.id === undefined){
            detailsObj = $scope.module.details[$scope.rowIndex];
        } else {
            detailsObj = $scope.module.details.find(function (v) {
                return v.id == $scope.inventory_movement_item.id;
            });
        }

        detailsObj.itemCode = details.itemCode;
        detailsObj.rate = details.rate;
        detailsObj.quantity = details.quantity;

        $scope.module.details[$scope.rowIndex] = detailsObj;
    };


    $scope.deleteRow = function (index) {
        $scope.inventory_movement_items.splice(index, 1);
        $scope.module.details.splice(index, 1);
    };

    $scope.resetTable = function () {
        var table = document.getElementById("transaction-details-table");

        while (table.rows.length-1 > 1) {
            table.deleteRow(1);
        }
    };

    $scope.reset = function () {
        $scope.module.transactionDate = new Date();
        $scope.module.remarks = "";
        $scope.inventory_movement_items = [];
        $scope.getItemList();
        $scope.resetTable();
    };

    $scope.resetForm = function () {
        $scope.reset();
    };
});