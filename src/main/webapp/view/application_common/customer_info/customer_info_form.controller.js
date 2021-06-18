app.controller('CustomerInfoFormCtrl', function ($scope, $http, $state, $timeout, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService, DialogBox, encrypt, Communication) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;


    $scope.module = {
        id: "",
        customerCode: "",
        customerName: "",
        email: "",
        phone: "",
        address: "",
        chartOfAccountsId: "",
        status: ""
    };


    if($state.current.name === JCOMPONENT.customer_info_update_view){

        var req = Communication.request("GET", API.CUSTOMER_INFO_GET + '/' + $stateParams.id, $scope.module);
        req.then(function (resp) {
            log("customer info edit: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;
            }
        }, function (err) {
            log("customer info edit error", JSON.stringify(err));
        });
    }


    $scope.saveModule = function () {

        var req;

        if($state.current.name === JCOMPONENT.customer_info_update_view){
            req = Communication.request("PUT", API.CUSTOMER_INFO_UPDATE, $scope.module);
        } else{
            req = Communication.request("POST", API.CUSTOMER_INFO_SAVE, $scope.module);
        }

        req.then(function (resp) {
            log("customer info: " + JSON.stringify(resp));
            if (resp.code === 200) {
                $scope.module = resp.body;
                $rootScope.toastSuccess("Successfully saved");
                $state.go(JCOMPONENT.customer_info_list_view);
            } else{
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            log("customer info error", JSON.stringify(err));
            $rootScope.toastError(err.message);
        });
    };


    $scope.reset = function() {

    };

});