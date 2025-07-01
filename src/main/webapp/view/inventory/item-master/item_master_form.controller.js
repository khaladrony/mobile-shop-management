app.controller('ItemMasterFormCtrl', function ($scope, $http, $state, $timeout, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService, DialogBox, encrypt, Communication) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;


    $scope.module = {
        id: "",
        itemCode: "",
        itemName: "",
        description: "",
        category: "",
        brand: "",
        model: "",
        color: "",
        storage: "",
        unit: "",
        price: "",
        standardPrice: "",
        standardCost: "",
        imei: "",
        active: ""
    };


    if($state.current.name === JCOMPONENT.item_master_update_view){

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

        var req;

        if($state.current.name === JCOMPONENT.item_master_update_view){
            req = Communication.request("PUT", API.ITEM_MASTER_UPDATE, $scope.module);
        } else{
            req = Communication.request("POST", API.ITEM_MASTER_SAVE, $scope.module);
        }

        req.then(function (resp) {
            log("user manage: " + JSON.stringify(resp));
            if (resp.code === 200) {
                $scope.module = resp.body;
                $rootScope.toastSuccess("Successfully saved");
                $state.go(JCOMPONENT.item_master_list_view);
            } else{
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            log("user error", JSON.stringify(err));
            $rootScope.toastError(err.message);
        });
    };


    $scope.reset = function() {

    };

});