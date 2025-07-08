app.controller('AppCodesFormCtrl', function ($scope, $http, $state, $timeout, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService, DialogBox, encrypt, Communication) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;
    $scope.module = {
        id: "",
        xtype: "",
        xcode: "",
        description: "",
        active: ""
    };

    if($state.current.name === JCOMPONENT.app_codes_update_view){
        var req = Communication.request("GET", API.APP_CODES_GET + '/' + $stateParams.id, $scope.module);
        req.then(function (resp) {
            log("Code edit: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;
            }
        }, function (err) {
            log("Code edit error", JSON.stringify(err));
        });
    }

    $scope.saveModule = function () {
        var req;

        if($state.current.name === JCOMPONENT.app_codes_update_view){
            req = Communication.request("PUT", API.APP_CODES_UPDATE, $scope.module);
        } else{
            req = Communication.request("POST", API.APP_CODES_SAVE, $scope.module);
        }

        req.then(function (resp) {
            log("App codes: " + JSON.stringify(resp));
            if (resp.code === 200) {
                $scope.module = resp.body;
                $rootScope.toastSuccess("Successfully saved");
                $state.go(JCOMPONENT.app_codes_list_view);
            } else{
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            log("user error", JSON.stringify(err));
            $rootScope.toastError(err.message);
        });
    };

    $scope.reset = function() {};
});