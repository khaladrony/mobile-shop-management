app.controller('InventoryMovementFormCtrl', function ($scope, $http, $state, $timeout, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService, DialogBox, encrypt, Communication,$filter,growl) {

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
        action: "",
        year: "",
        month: "",
        status: "",
        remarks: "",
        supplierCode: "",
        customerCode: "",
        createOn: new Date(),
        details: []
    };

    $scope.inventory_movement_item = {
        itemCode: "",
        quantity: "",
        createOn: new Date()
    };

    $scope.isWarehouseHide = true;

    $scope.getCoaList = function () {
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

    $scope.movementItemsObj = function () {
        var details = [];
        details.itemCode = $scope.inventory_movement_item.itemCode;
        details.quantity = $scope.inventory_movement_item.quantity;
        details.item_name_code = $scope.item_name_code;

        $scope.inventory_movement_items.push(details);
    };

    $scope.dropDownSelectText = function (elementid) {
        var s1 = document.getElementById(elementid);
        var text = s1.options[s1.selectedIndex].text.substring(0);
        return text;
    };

    $scope.clearTaxBox = function () {
        $scope.inventory_movement_items.itemCode = "";
        $scope.inventory_movement_items.quantity = "";
        $scope.inventory_movement_items.item_name_code = "";
    };

    $scope.saveModule = function () {

        if(!$scope.saveValidation()){
            return;
        }

        var req;
        if($state.current.name === JCOMPONENT.inventory_movement_update_view) {
            req = Communication.request("PUT", API.INVENTORY_MOVEMENT_UPDATE, $scope.module);
        } else{
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
            log("voucher edit: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;

                $scope.getGridVoucherDetailsForEdit($scope.module.details);
            }
        }, function (err) {
            log("voucher edit error", JSON.stringify(err));
        });
    }

    $scope.getGridVoucherDetailsForEdit = function (voucherDetails) {

        var details = [];
        angular.forEach(voucherDetails, function (value, key) {
            details.chartOfAccountsId = value.chartOfAccountsId;
            details.chartOfAccountsCodeName = value.chartOfAccountsCodeName;
            details.chartOfAccountsSource = value.chartOfAccountsSource;

            if (value.subAccountsId != null) {
                details.subAccountsId = value.subAccountsId;
                details.subAccountsCodeName = value.subAccountsCodeName;
            }

            details.particulars = value.particulars;
            details.amount = value.amount;
            details.primeAmount = value.primeAmount;
            details.baseAmount = value.baseAmount;
            details.id = value.id;

            $scope.voucher_details_list.push(details);
            details = [];
        });

        if ($scope.module.paymentType === VOUCHER_KEY.PAYMENT_TYPE.BANK) {
            $scope.isBankHide = false;
            $scope.getBankAccountList();
        } else {
            $scope.isBankHide = true;
        }

    };

    $scope.rowDataPopulate = function (rowData, rowIndex) {

        $scope.rowIndex = rowIndex;
        $scope.detailsAddBtnHide = true;
        $scope.detailsEditBtnHide = false;

        $scope.voucher_details.chartOfAccountsId = rowData.chartOfAccountsId;
        $scope.voucher_details.chartOfAccountsSource = rowData.chartOfAccountsSource;
        $scope.voucher_details.particulars = rowData.particulars;
        $scope.voucher_details.amount = rowData.amount;
        $scope.voucher_details.id = rowData.id;

        if (rowData.subAccountsId === "" || rowData.subAccountsId === undefined) {
            $scope.isSubAccHide = true;
            $scope.particularsDivClassVar = "col-xs-12 col-md-6";
            $scope.voucher_details.subAccountsId = $scope.subaccountList[0];
        } else {
            $scope.getSubAccountList(rowData.chartOfAccountsSource, rowData.chartOfAccountsId);
            $scope.particularsDivClassVar = "col-xs-12 col-md-3";
            $scope.isSubAccHide = false;
            $scope.voucher_details.subAccountsId = rowData.subAccountsId;
        }

    };

    $scope.editToGrid = function () {

        $scope.detailsAddBtnHide = false;
        $scope.detailsEditBtnHide = true;
        var details = [];

        details.chartOfAccountsId = $scope.voucher_details.chartOfAccountsId;
        details.chartOfAccountsCodeName = $scope.dropDownSelectText("chartOfAccountsId");

        if ($scope.voucher_details.subAccountsId != "" && $scope.voucher_details.subAccountsId != undefined) {
            details.subAccountsId = $scope.voucher_details.subAccountsId;
            details.subAccountsCodeName = $scope.dropDownSelectText("subAccountsId");
        }

        details.particulars = $scope.voucher_details.particulars;
        details.amount = $scope.voucher_details.amount;
        details.primeAmount = $scope.voucher_details.amount * $scope.voucher_details.currencyRate;
        details.baseAmount = $scope.voucher_details.amount;
        details.id = $scope.voucher_details.id;
        $scope.voucher_details_list[$scope.rowIndex] = details;

        $scope.voucherDetailsUpdate(details);

        $scope.firstRowAmountUpdate();

        $scope.amountInWords(details);

        $scope.clearTaxBox();
    };

    $scope.voucherDetailsUpdate = function (details) {
        var detailsObj;

        if(details.id === undefined){
            detailsObj = $scope.module.details[$scope.rowIndex];
        } else {
            detailsObj = $scope.module.details.find(function (v) {
                return v.id == $scope.voucher_details.id;
            });
        }

        detailsObj.chartOfAccountsId = details.chartOfAccountsId;
        detailsObj.chartOfAccountsCodeName = details.chartOfAccountsCodeName;
        detailsObj.particulars = details.particulars;
        if (details.subAccountsId != "" && details.subAccountsId != undefined) {
            detailsObj.subAccountsId = details.subAccountsId;
            detailsObj.subAccountsCodeName = details.subAccountsCodeName;
        }
        detailsObj.particulars = details.particulars;
        detailsObj.amount = details.amount;
        detailsObj.primeAmount = details.primeAmount;
        detailsObj.baseAmount = details.baseAmount;

        $scope.module.details[$scope.rowIndex] = detailsObj;
    };

    $scope.firstRowAmountUpdate = function () {

        var voucherAmount = $scope.voucherAmount();
        var voucherDetailFirstRowData = $scope.module.details[0];
        voucherDetailFirstRowData.amount = voucherAmount;
        voucherDetailFirstRowData.primeAmount = (-1) * voucherAmount * $scope.voucher_details.currencyRate;
        voucherDetailFirstRowData.baseAmount = (-1) * voucherAmount;

        $scope.module.details[0] = voucherDetailFirstRowData;
    };

    $scope.deleteRow = function (index) {

        $scope.voucher_details_list.splice(index, 1);

        $scope.module.details.splice(index, 1);
        $scope.firstRowAmountUpdate();
        $scope.amountInWords();

    };

    $scope.resetTable = function () {
        var table = document.getElementById("voucher-details-table");

        while (table.rows.length-1 > 1) {
            table.deleteRow(1);
        }
    };

    $scope.reset = function () {
        $scope.voucher_details_list = [];
        $scope.bankList = [];
        $scope.coaList = [];
        $scope.subaccountList = [];
        $scope.isBankHide = true;
        $scope.isSubAccHide = true;
        $scope.module.particulars = "";
        $scope.module.chequeNo = "";
        $scope.module.chequeDate = "";
        $('#amountInWords').val("");
        $('table tfoot td').eq($scope.table_debit_index).text(0);
        $('table tfoot td').eq($scope.table_credit_index).text(0);
        $scope.particularsDivClassVar = "col-xs-12 col-md-6";

        $scope.getCoaList();
        $scope.resetTable();
    };

    $scope.resetForm = function () {
        $scope.module.paymentType = VOUCHER_KEY.PAYMENT_TYPE.CASH;
        $scope.reset();
    };



});