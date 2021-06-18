app.controller('EmployeeInfoFormCtrl', function ($scope, $http, $state, $timeout, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService, DialogBox, encrypt, Communication) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;


    $scope.module = {
        id: "",
        employeeCode: "",
        employeeName: "",
        employeePin: "",
        designation: "",
        email: "",
        phone: "",
        address: "",
        chartOfAccountsId: "",
        status: ""
    };


    if($state.current.name === JCOMPONENT.employee_info_update_view){

        var req = Communication.request("GET", API.EMPLOYEE_INFO_GET + '/' + $stateParams.id, $scope.module);
        req.then(function (resp) {
            log("employee info edit: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;
            }
        }, function (err) {
            log("employee info edit error", JSON.stringify(err));
        });
    }


    $scope.saveModule = function () {

        var req;

        if($state.current.name === JCOMPONENT.employee_info_update_view){
            req = Communication.request("PUT", API.EMPLOYEE_INFO_UPDATE, $scope.module);
        } else{
            req = Communication.request("POST", API.EMPLOYEE_INFO_SAVE, $scope.module);
        }

        req.then(function (resp) {
            log("employee info: " + JSON.stringify(resp));
            if (resp.code === 200) {
                $scope.module = resp.body;
                $rootScope.toastSuccess("Successfully saved");
                $state.go(JCOMPONENT.employee_info_list_view);
            } else{
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            log("employee info error", JSON.stringify(err));
            $rootScope.toastError(err.message);
        });
    };


    $scope.reset = function() {

    };

});