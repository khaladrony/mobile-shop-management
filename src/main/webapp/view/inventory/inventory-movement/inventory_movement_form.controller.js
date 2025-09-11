app.controller('InventoryMovementFormCtrl', function ($scope, $http, $state, $timeout,
                $q, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService,
                DialogBox, encrypt, Communication,$filter,growl, ItemService,
                ToasterMessageQueueService, DateHelperService, SupplierService, DefaultSetupService
                ) {

    copyPasteStringRestrict('.pastedString');
    $rootScope.setPageName(JMODULE_NAME, $state.current.name);
    $scope.current_state = $state.current.name;

    $scope.itemList = [];
    $scope.inventory_movement_items = [];
    $scope.item_name_code = "";
    $scope.isIMEI = false;
    $scope.imeiNumbers = [];

    $scope.module = {
        id:"",
        transactionId: "",
        transactionDate: new Date(),
        reference: "",
        warehouse: null,
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

    $scope.isWarehouseHide = false;
    $scope.detailsAddBtnHide = false;
    $scope.detailsEditBtnHide = true;

    DefaultSetupService.get('inventoryDefault').then(function(data) {
        $scope.inventorySetup = data;
        console.log("Inventory Default loaded", data);
    });

    $scope.getItemList = function () {
        DialogBox.showProgress();
        return ItemService.getItemList()
            .then(function (items) {
                 DialogBox.hideProgress();
                $scope.itemList = items;
           }).catch(function (err) {
                log("Item list error", err);
           }).finally(function () {
                DialogBox.hideProgress();
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
        details.imeiNumbers = [];

        $scope.inventory_movement_items.push(details);

        $scope.module.details.push(angular.copy($scope.inventory_movement_item));
    };

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
        $scope.inventory_movement_item.selectedItem = $scope.itemList
                    .find(item => item.item_code === rowData.itemCode);
        $scope.inventory_movement_item.rate = rowData.rate;
        $scope.inventory_movement_item.quantity = rowData.quantity;
        $scope.inventory_movement_item.imeiNumbers = rowData.imeiNumbers;
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
        details.imeiNumbers = $scope.inventory_movement_item.imeiNumbers;
        $scope.inventory_movement_items[$scope.rowIndex] = details;

        $scope.transactionUpdate(details);

        $scope.clearTaxBox();
    };

    $scope.saveModule = function () {
        if(!$scope.saveValidation()){
            return;
        }
        //Avoid time zone
        $scope.module.transactionDate = DateHelperService.formatToLocalDateTimeString($scope.module.transactionDate);

        var req;
        if($state.current.name === JCOMPONENT.inventory_movement_update_view) {
            req = Communication.request("PUT", API.INVENTORY_MOVEMENT_UPDATE, $scope.module);
        } else{
          req = Communication.request("POST", API.INVENTORY_MOVEMENT_SAVE, $scope.module);
        }

        req.then(function (resp) {
            log("Inventory movement: " + JSON.stringify(resp));
            if (resp.code === 200) {
                $scope.reset();
                $scope.module.details = [];

                if($state.current.name === JCOMPONENT.inventory_movement_update_view){
                    $state.go(JCOMPONENT.inventory_movement_list_view);
                    ToasterMessageQueueService.addMessage('success', 'Successfully updated', 'Success!');
                } else{
                    growl.success('Successfully saved',{title: 'Success!'});
                }

            } else{
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            log("Inventory movement save error", JSON.stringify(err));
            $rootScope.toastError(err.message);
        }).finally(function () {
            if (typeof $scope.module.transactionDate === 'string') {
                $scope.module.transactionDate = new Date($scope.module.transactionDate);
            }
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
            if (resp.code === 200) {
                $scope.module = resp.body;
                //Date string convert to JS Date object
                $scope.module.transactionDate = new Date(resp.body.transactionDate);
                $scope.module.selectedSupplier = $scope.suppliers
                                    .find(supplier => supplier.code === resp.body.supplierCode);
                $scope.getGridTransactionItemsForEdit($scope.module.details);
            }
        }, function (err) {
            log("Receive transaction edit error", JSON.stringify(err));
        });
    }

    $scope.getGridTransactionItemsForEdit = function (transactionItems) {
        var details = [];
        angular.forEach(transactionItems, function (value, key) {
            details.id = value.id;
            details.itemCode = value.itemCode;
            details.rate = value.rate;
            details.quantity = value.quantity;
            details.item_name_code = $scope.itemList.find(
                    item => item.item_code === value.itemCode).item_name_code;

            details.imeiNumbers = value.imeiNumbers;

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
        $scope.module.warehouse = null;
        $scope.module.remarks = "";
        $scope.module.supplierCode = "";
        $scope.module.reference = "";
        $scope.inventory_movement_items = [];
        $scope.resetTable();
    };

    $scope.resetForm = function () {
        $scope.reset();
    };

     // Temporary IMEI list for modal
     $scope.tempImeiList = null;
     $scope.imei = { imeiValue: "",  itemCode: "", qty:""};
     $scope.selectedItemIndex = null;

     // Open modal
     $scope.openImeiModal = function(index) {
         $scope.selectedItemIndex = index;
         let currentImeis = $scope.inventory_movement_items[index].imeiNumbers || [];
         $scope.imei.itemCode = $scope.inventory_movement_items[index].itemCode;
         $scope.imei.qty = Number($scope.inventory_movement_items[index].quantity || 0);
         $scope.tempImeiList = angular.copy(currentImeis);
         $('#imeiModal').modal('show');
     };

     // Add IMEI to list
     $scope.addTempImei = function() {
         if ($scope.imei.imeiValue && $scope.imei.imeiValue.trim() !== "") {
             let newValue = $scope.imei.imeiValue.trim();
             let itemCode = $scope.imei.itemCode;

             if($scope.tempImeiList.length > 0
                && ($scope.tempImeiList.length + 1) !== $scope.imei.qty) {

                $scope.showToast("❌ Number of IMEIs must match the quantity!", "danger");
                return;
            }

             var exists = $scope.tempImeiList.some(function(item) {
                 return item.imeiValue === newValue;
             });

             if (exists) {
                 $scope.showToast("❌ Duplicate IMEI not allowed: " + newValue, "danger");
                 return;
             }

            $scope.tempImeiList.push({
                itemCode: itemCode,
                imeiValue: newValue,
                status: "AVAILABLE"
            });
            $scope.imei.imeiValue = "";
         }
     };

     // Remove IMEI
     $scope.removeTempImei = function(index) {
         $scope.tempImeiList.splice(index, 1);
     };

     // Save IMEIs back to row
     $scope.saveImeis = function() {
         if ($scope.selectedItemIndex !== null) {
             $scope.inventory_movement_items[$scope.selectedItemIndex].imeiNumbers = angular.copy($scope.tempImeiList);
             $scope.module.details[$scope.selectedItemIndex].imeiNumbers = angular.copy($scope.tempImeiList);
         }
         $('#imeiModal').modal('hide');
     };

     $scope.getImeiValues = function(item) {
         if (!item.imeiNumbers || !item.imeiNumbers.length) {
             return "";
         }
         return item.imeiNumbers.map(function(i) {
             return i.imeiValue;
         }).join(", ");
     };

     $scope.resetImeiModal = function () {
         $scope.tempImeiList = null;
         $scope.imei.imeiValue = "";
     }

     /* IMEI number edit*/
     $scope.editingIndex = -1;
     $scope.imeiEdit ={ editedValue : "" }

     $scope.startEdit = function(index, imei) {
         $scope.editingIndex = index;
         $scope.imeiEdit.editedValue = imei.imeiValue;
     };

     $scope.saveEdit = function(index) {
         if ($scope.imeiEdit.editedValue && $scope.imeiEdit.editedValue.trim() !== "") {
             var newValue = $scope.imeiEdit.editedValue.trim();

             var exists = $scope.tempImeiList.some(function(item, i) {
                 return i !== index && item.imeiValue === newValue;
             });

             if (exists) {
                 $scope.showToast("❌ Duplicate IMEI not allowed: " + newValue, "danger");
                 return;
             }

             $scope.tempImeiList[index].imeiValue = newValue;
         }
         $scope.editingIndex = -1;
         $scope.imeiEdit.editedValue = "";
     };

     $scope.cancelEdit = function() {
         $scope.editingIndex = -1;
         $scope.imeiEdit.editedValue = "";
     };

//     Error message show
    $scope.toastMessage = "";
    $scope.toastType = "success"; // or "danger"

    $scope.showToast = function(message, type) {
        $scope.toastMessage = message;
        $scope.toastType = type;

        // auto clear after 2.5 sec
        setTimeout(function() {
            $scope.toastMessage = "";
            $scope.$applyAsync();
        }, 2500);
    };


     /*Supplier info*/
     $scope.getSupplier = function() {
        return SupplierService.getSupplierList()
            .then(function(response) {
                $scope.suppliers = response;
            })
            .catch(function(err) {
                $scope.showMessage('failed', err, 6000);
            });
     };

     $scope.fetchSupplier = function(params) {
         const offset = params.page * params.limit;
         const search = params.search.toLowerCase();
         const limit = params.limit;

         return $q(function(resolve) {
             $timeout(function() {
                 let filtered = $scope.suppliers;
                 if (search) {
                     filtered = filtered.filter(item =>
                         item.name.toLowerCase().includes(search)
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

     $scope.onSupplierChange = function(item) {
        $scope.module.supplierCode = item.code;
         /*$scope.inventory_movement_item.itemCode = item.item_code;
         $scope.selectItem($scope.inventory_movement_item.itemCode);*/
     };

     $scope.init = function () {
         $scope.getItemList();
         $scope.getSupplier();
     };

     $scope.init();
});