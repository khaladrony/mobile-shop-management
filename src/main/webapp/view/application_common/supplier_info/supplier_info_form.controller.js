app.controller('SupplierInfoFormCtrl', function ($scope, $http, $state, $timeout, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService, DialogBox, encrypt, Communication) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;


    $scope.module = {
        id: "",
        supplierCode: "",
        supplierName: "",
        email: "",
        phone: "",
        address: "",
        chartOfAccountsId: "",
        status: ""
    };


    if($state.current.name === JCOMPONENT.supplier_info_update_view){

        var req = Communication.request("GET", API.SUPPLIER_INFO_GET + '/' + $stateParams.id, $scope.module);
        req.then(function (resp) {
            log("supplier info edit: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;
            }
        }, function (err) {
            log("supplier info edit error", JSON.stringify(err));
        });
    }


    $scope.saveModule = function () {

        var req;

        if($state.current.name === JCOMPONENT.supplier_info_update_view){
            req = Communication.request("PUT", API.SUPPLIER_INFO_UPDATE, $scope.module);
        } else{
            req = Communication.request("POST", API.SUPPLIER_INFO_SAVE, $scope.module);
        }

        req.then(function (resp) {
            log("supplier info: " + JSON.stringify(resp));
            if (resp.code === 200) {
                $scope.module = resp.body;
                $rootScope.toastSuccess("Successfully saved");
                $state.go(JCOMPONENT.supplier_info_list_view);
            } else{
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            log("supplier info error", JSON.stringify(err));
            $rootScope.toastError(err.message);
        });
    };


    $scope.reset = function() {

    };

});