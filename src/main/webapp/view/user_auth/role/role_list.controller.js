app.controller('RoleListCtrl', function ($scope, $http, $state, $timeout, $rootScope, $mdDialog, $interval, Communication) {
    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.list = [];

    
    $scope.getDataList = function () {
        var req = Communication.request("GET", API.ROLE_LIST, {});
        req.then(function (resp) {
            log("role list: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.list = resp.body;
            }

        }, function (err) {
            log("role error", JSON.stringify(err));
        });
    };

    $scope.showEditForm = function (obj) {
        $state.go(JCOMPONENT.role_update, {role_id: obj.role_id});
    };

    $scope.activeInactive = function (obj, val) {

        obj.active = val;
        var req = Communication.request("PUT", API.ROLE_UPDATE + '/' + obj.role_id, obj);

        req.then(function (resp) {
            log("role active/inactive: " + JSON.stringify(resp));
            $scope.getDataList();
        }, function (err) {
            log("role error", JSON.stringify(err));
        });
    };
    
    
    $scope.downloadRoleWisePermission = function (obj, fileName) {
        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", API.ROLE_PERMISSION_XLS + "/" + obj.role_id, true);
        xhttp.setRequestHeader('x-aip-token', _shskr_);
        xhttp.responseType = 'blob';
        xhttp.onload=function(e){
            if (this.status === 200) {
                var campaign = new Blob([this.response], {type: 'application/vnd.ms-excel'});
                // const campaignURL = URL.createObjectURL(campaign);
                // window.open(campaignURL);
                var link = document.createElement('a');
                link.href = window.URL.createObjectURL(campaign);
                link.download = fileName;
                link.click();
            }
        };
        xhttp.send();
    };
    
    $scope.downloadRoleWiseUser = function (obj, fileName) {
        
        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", API.ROLE_USERLIST_XLS + "/" + obj.role_id, true);
        xhttp.setRequestHeader('x-aip-token', _shskr_);
        xhttp.responseType = 'blob';
        xhttp.onload=function(e){
            if (this.status === 200) {
                var campaign = new Blob([this.response], {type: 'application/vnd.ms-excel'});
                // const campaignURL = URL.createObjectURL(campaign);
                // window.open(campaignURL);
                var link = document.createElement('a');
                link.href = window.URL.createObjectURL(campaign);
                link.download = fileName;
                link.click();
            }
        };
        xhttp.send();
    };

});