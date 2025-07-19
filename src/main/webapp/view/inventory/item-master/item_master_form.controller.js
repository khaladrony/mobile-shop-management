app.controller('ItemMasterFormCtrl', function ($scope, $http, $state, $timeout,
                $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService,
                DialogBox, encrypt, Communication, growl, ItemService) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;
    $scope.module = {
        id: "",
        itemCode: "",
        itemName: "",
        description: "",
        category: null,
        brand: null,
        model: "",
        color: null,
        storage: "",
        unit: null,
        price: "",
        standardPrice: "",
        standardCost: "",
        imei: "",
        active: true
    };

    if($state.current.name === JCOMPONENT.item_master_update_view) {
        var req = Communication.request("GET", API.ITEM_MASTER_GET + '/' + $stateParams.id, $scope.module);
        req.then(function (resp) {
            log("Item master edit: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;
            }
        }, function (err) {
            log("Item master edit error", JSON.stringify(err));
        });
    }

    $scope.saveModule = function () {
        if(!$scope.saveValidation()) {
            return;
        }

        var req;

        if($state.current.name === JCOMPONENT.item_master_update_view){
            req = Communication.request("PUT", API.ITEM_MASTER_UPDATE, $scope.module);
        } else{
            req = Communication.request("POST", API.ITEM_MASTER_SAVE, $scope.module);
        }

        req.then(function (resp) {
            log("user manage: " + JSON.stringify(resp));
            if (resp.code === 200) {
                growl.success('Successfully saved',{title: 'Success!'});
                $scope.module = resp.body;
                $rootScope.toastSuccess("Successfully saved");
                //Item cache clear
                ItemService.clearCache();

                $state.go(JCOMPONENT.item_master_list_view);
            } else{
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            log("user error", JSON.stringify(err));
            $rootScope.toastError(err.message);
        });
    };

     $scope.saveValidation = function () {
        if ($scope.module.itemName === '') {
            growl.error('Please enter item name',{title: 'Error!'});
            return false;
        }
        return true;
     };

    $scope.reset = function() {};
});