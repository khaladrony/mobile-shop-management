app.controller('InventoryReportItemLedgerFormCtrl', function (
                $scope, $http, $state, $timeout, $stateParams, $rootScope, $sce,$mdDialog,
                $interval, ClientService, DialogBox, Communication, growl, DateHelperService,
                ItemService, $q) {
    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;

    $scope.itemList = [];
    $scope.item_name_code = "";
    $scope.search = {
        fromDate: "",
        toDate: "",
        warehouse: null,
        itemCode: "",
        reportType: null
    };

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
        $scope.search.itemCode = item.item_code;
    };

    $scope.formFieldValidation = function () {
        if ($scope.search.fromDate === '') {
            growl.error('From date required', {title: 'Error!'});
            return false;
        }

        if ($scope.search.toDate === '') {
            growl.error('To date required', {title: 'Error!'});
            return false;
        }

        if ($scope.search.warehouse === ''
            || $scope.search.warehouse === null) {
            growl.error('Warehouse required', {title: 'Error!'});
            return false;
        }

        return true;
    };

    $scope.itemLedgerPreview = function () {
        if(!$scope.formFieldValidation()){
            return;
        }

         const result = DateHelperService.validateAndFormat($scope.search.fromDate, $scope.search.toDate);
         if (!result) return;

        var params = new URLSearchParams();
        params.append("fromDate", result.fromDate);
        params.append("toDate", result.toDate);
        params.append("warehouse", $scope.search.warehouse);
        if ($scope.search.itemCode) {
            params.append("itemCode", $scope.search.itemCode);
        }
        if ($scope.search.reportType) {
            params.append("reportType", $scope.search.reportType);
        }

        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", API.INVENTORY_REPORT_ITEM_LEDGER + "?" + params.toString(), true);
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