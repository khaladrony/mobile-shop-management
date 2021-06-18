app.controller('FeatureListCtrl', function ($scope, $http, $state, $timeout, $rootScope, $mdDialog, $interval, Communication) {
    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.list = [];
    $scope.moduleList = [];
    $scope.module = "ALL";
    
    $scope.getDataList = function () {
        var req = Communication.request("GET", API.FEATURE_LIST, {});
        req.then(function (resp) {
            
            if (resp.code === 200) {
                $scope.list = resp.body;
                
                for(var i=0 ; i<resp.body.length ; i++){
                    log("feature:" + JSON.stringify(resp.body[i]));
                    var name = resp.body[i].feature_name.trim();
                    
                    if( resp.body[i].type === "Module" && !$scope.existModule(name) ){
                        $scope.moduleList.push(name);
                    }
                }
                
                log("module list: " + JSON.stringify($scope.moduleList));
            }

        }, function (err) {
            log("feature error", JSON.stringify(err));
        });
    };
    
    $scope.existModule = function(mName){
        for(var i=0 ; i<$scope.moduleList.length ; i++){
            if( $scope.moduleList[i] === mName ){
                return true;
            }
        }
        return false;
    };
    
    

    $scope.showEditForm = function (obj) {
        $state.go(JCOMPONENT.feature_update, {feature_id: obj.feature_id});
    };

    $scope.activeInactive = function (obj, val) {

        obj.active = val;
        var req = Communication.request("PUT", API.FEATURE_UPDATE + '/' + obj.feature_id, obj);

        req.then(function (resp) {
            log("feature active/inactive: " + JSON.stringify(resp));

            $scope.getDataList();

        }, function (err) {
            log("feature error", JSON.stringify(err));
        });
    };

});