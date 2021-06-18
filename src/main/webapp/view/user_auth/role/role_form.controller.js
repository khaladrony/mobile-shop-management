app.controller('RoleFormCtrl', function ($scope, $http, $state, $timeout, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService, DialogBox, Communication) {
    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;
    
    $scope.module = {
        role_id: "",
        role_name: "",
        role_code: new Date().getTime(),
        note: ""
    };
    
    if($state.current.name === JCOMPONENT.role_update){
        var req = Communication.request("GET", API.ROLE_GET + '/' + $stateParams.role_id, $scope.module);

        req.then(function (resp) {
            log("role edit: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;
            } else{
                
            }
            
        }, function (err) {
            log("role edit error", JSON.stringify(err));
        });
    }
    
    
    $scope.saveModule = function () {
        var req;
        
        if($state.current.name === JCOMPONENT.role_update){
            req = Communication.request("PUT", API.ROLE_UPDATE + '/' + $scope.module.role_id, $scope.module);
        } else{
            req = Communication.request("POST", API.ROLE_SAVE, $scope.module);
        }
        
        req.then(function (resp) {
            log("role manage: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;
                $state.go(JCOMPONENT.role_list_view);
                
                $rootScope.toastSuccess("Successfully saved");
            } else{
                $rootScope.toastWarning(resp.message);
            }
            
            
        }, function (err) {
            log("role error", JSON.stringify(err));
        });
    };


});