app.controller('AppSettingsCtrl', function ($scope, $http, $state, $timeout, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService, DialogBox, encrypt, Communication, $window) {
    $state.current.name = "app_settings_view";
    angular.element('#page_name').html("App Settings");
    $scope.current_state = $state.current.name;
    
    $scope.module = {
        id: "",
        pass_exp: "",
        pass_exp_alert: "",
        sess_timeout: "",
        default_pkey: "",
        active: 1
    };

    $scope.clicked = function () {
        $window.location.href = _baseurl_ + '/dashboard/statics/view#!/dashboard_static_view';
    };
    
    $scope.getSettigsData = function(){
        
        var req = Communication.request("GET", API.APP_SETTS_GET, {});
        req.then(function (resp) {
            if (resp.code === 200) {
                $scope.module = resp.body;
                $scope.module.password = "";
            }
        }, function (err) {
            log("app settings edit error", JSON.stringify(err));
        });
    };
    
    
    $scope.saveSettings = function () {
        
        if( $scope.module.pass_exp === "" ){
            $rootScope.toastError("Password expiry (in days) is mandatory");
            return;
        }
        
        if( $scope.module.pass_exp_alert === "" ){
            $rootScope.toastError("Password expiry alert (in days) is mandatory");
            return;
        }
        
        if( $scope.module.sess_timeout === "" ){
            $rootScope.toastError("Sessiom timeout (in minutes) is mandatory");
            return;
        }
        
        if( $scope.module.pass_exp === "" ){
            $rootScope.toastError("Default password is mandatory");
            return;
        }
        
        
        var req = Communication.request("PUT", API.APP_SETTS_SAVE, $scope.module);
        req.then(function (resp) {
            log("app settings: " + JSON.stringify(resp));
            if (resp.code === 200) {
                $rootScope.toastSuccess("Successfully saved");
            } else{
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            log("app settings error", JSON.stringify(err));
            $rootScope.toastError(err.message);
        });
    };
    

});