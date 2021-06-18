app.controller('RoleFeatureCtrl', function ($scope, $http, $state, $timeout, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService, DialogBox, Communication) {
    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.roleList = [];
    $scope.featureList = [];
    $scope.selected_role_id = "";
    $scope.default_home_feature = "";
    
    $scope.formObj = {
        role_id:"",
        feature_list:[]
    };
    
    
    $scope.getRoleList = function () {
        var req = Communication.request("GET", API.ROLE_LIST, {});

        req.then(function (resp) {
            log("role list: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.roleList = resp.body;
            }

        }, function (err) {
            log("role list error", JSON.stringify(err));
        });
    };
    
    $scope.getFeatureList = function (role_id) {
        $scope.featureList = [];
        DialogBox.showProgress();
        $scope.selected_role_id = role_id;
        var req = Communication.request("GET", API.ROLE_FEATURE_LIST + "/" + role_id, {});

        req.then(function (resp) {
            DialogBox.hideProgress();
            log("featureList: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.featureList = resp.body;
            }

        }, function (err) {
            DialogBox.hideProgress();
            log("featureList error", JSON.stringify(err));
        });
    };
    
    
    $scope.setChecked = function(feature_id, is_selected, module_id, feature_group_id,full_path){
        log(module_id + " ; " + feature_group_id + " ; " + feature_id + " : " + is_selected + " : ");
        $scope.default_home_feature = "";
        
        for(var i=0 ; i<$scope.featureList.length ; i++){
            if( $scope.featureList[i].feature_id === feature_id){
                log("matched", feature_id);
                $scope.featureList[i].is_selected = is_selected;
                $scope.featureList[i].module_id = module_id;
                $scope.featureList[i].feature_group_id = feature_group_id;
                $scope.featureList[i].full_path = full_path;
                break;
            }
        }
        
        $scope.showListForDefault();
    };
    
    $scope.showListForDefault = function(){
        
        var vilStr = "<option value=''>Choose</option>";
        for(var i=0 ; i<$scope.featureList.length ; i++){
            var obj = $scope.featureList[i];
            
            if( obj.is_selected && obj.feature_group_id !== undefined && obj.feature_id > 0 ){
                if(obj.is_home){
                    $scope.default_home_feature = obj.feature_id;
                  vilStr += "<option selected='selected' value='" + obj.feature_id + "'>" +obj.full_path+ "</option>";    
                }else{
                    vilStr += "<option value='" + obj.feature_id + "'>" +obj.full_path+ "</option>";
                }
            }
        }
        
        angular.element("select#default_home").empty().append(vilStr);
        angular.element("select#default_home").val($scope.default_home_feature);
        angular.element("select#default_home").trigger('change');
        
    };
    
    $scope.selectDefaultHome = function(feature_id){
        $scope.default_home_feature = feature_id;
    };
    
    $scope.saveRoleFeatures = function(){
        
        if( $scope.default_home_feature === "" ){
            DialogBox.alert("Warning", "Select default home feature of that Role");
            return;
        }
        
        
        DialogBox.showProgress();
        
        var selected = new Set();
        for(var i=0 ; i<$scope.featureList.length ; i++){
            var obj = $scope.featureList[i];
            
            if( obj.is_selected && obj.feature_group_id !== undefined ){
                if( $scope.featureList[i].module_id !== undefined && $scope.featureList[i].module_id > 0 ) selected.add($scope.featureList[i].module_id);
                if( $scope.featureList[i].feature_group_id !== undefined &&  $scope.featureList[i].feature_group_id > 0 ) selected.add($scope.featureList[i].feature_group_id);
                if( $scope.featureList[i].feature_id > 0 ) selected.add($scope.featureList[i].feature_id);
            }
        }
        
        var req = Communication.request("POST", API.ROLE_FEATURE_MAP, {"role_id":$scope.selected_role_id, "default_home":$scope.default_home_feature, "features":Array.from(selected)});

        req.then(function (resp) {
            DialogBox.hideProgress();
            log("featureList: " + JSON.stringify(resp));
            
            if (resp.code === 200) {
                $rootScope.toastSuccess("Successfully saved");
            } else{
                $rootScope.toastWarning("unable to save. Try again later");
            }

        }, function (err) {
            DialogBox.hideProgress();
            log("featureList error", JSON.stringify(err));
            $rootScope.toastError("unable to save. Try again later");
        });
    };
    
});