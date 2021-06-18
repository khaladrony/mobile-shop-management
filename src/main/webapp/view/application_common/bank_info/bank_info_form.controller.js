app.controller('BankInfoFormCtrl', function ($scope, $http, $state, $timeout, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService, DialogBox, encrypt, Communication) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;


    $scope.module = {
        id: "",
        bankAccountCode: "",
        bankAccountNo: "",
        bankAccountName: "",
        bankName: "",
        branchName: "",
        phone: "",
        address: "",
        status: ""
    };


    if($state.current.name === JCOMPONENT.bank_info_update_view){

        var req = Communication.request("GET", API.BANK_INFO_GET + '/' + $stateParams.id, $scope.module);
        req.then(function (resp) {
            log("Bank a/c edit: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;
            }
        }, function (err) {
            log("bank a/c edit error", JSON.stringify(err));
        });
    }


    $scope.saveModule = function () {

        var req;

        if($state.current.name === JCOMPONENT.bank_info_update_view){
            req = Communication.request("PUT", API.BANK_INFO_UPDATE, $scope.module);
        } else{
            req = Communication.request("POST", API.BANK_INFO_SAVE, $scope.module);
        }

        req.then(function (resp) {
            log("user manage: " + JSON.stringify(resp));
            if (resp.code === 200) {
                $scope.module = resp.body;
                $rootScope.toastSuccess("Successfully saved");
                $state.go(JCOMPONENT.bank_info_list_view);
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