app.controller('AppAuditCtrl', function ($scope, $http, $state, $timeout, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService, DialogBox, encrypt, Communication) {
    $state.current.name = "app_audit_view";
    angular.element('#page_name').html("App Audit");
    $scope.current_state = $state.current.name;
    
    $scope.dataList = [];
    $scope.userList = [];
    $scope.module = {
        user_id: "",
        start_date: "",
        end_date: ""
    };
    
    $scope.getUserList = function () {
        var req = Communication.request("GET", API.USER_LIST, {});
        req.then(function (resp) {
            log("user list: " + JSON.stringify(resp));
            if (resp.code === 200) {
                $scope.userList = resp.body;
            }
        }, function (err) {
            log("user list error", JSON.stringify(err));
        });
    };
    
    
    $scope.getAuditData = function(){
        
        var req = Communication.request("POST", API.APP_AUDIT_GET, $scope.module);
        req.then(function (resp) {
            if (resp.code === 200) {
                $scope.dataList = resp.body;
            } else{
                $scope.dataList = [];
            }
        }, function (err) {
            log("app audit edit error", JSON.stringify(err));
        });
    };
    
    $scope.showDetails = function(obj){
        
        DialogBox.alert("Audit log", JSON.stringify(obj, null, 4));        
    };
    
    $scope.downloadUserWiseAudit = function (obj, fileName) {
        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", API.USER_AUDIT_XLS + "/" + obj.user_id, true);
        xhttp.setRequestHeader('x-aip-token', _shskr_);
        xhttp.responseType = 'blob';
        xhttp.onload=function(e){
            if (this.status === 200) {
                var auditData = new Blob([this.response], {type: 'application/vnd.ms-excel'});
                // const auditURL = URL.createObjectURL(auditData);
                // window.open(auditURL);
                var link = document.createElement('a');
                link.href = window.URL.createObjectURL(auditData);
                link.download = fileName;
                link.click();
            }
        };
        xhttp.send();
    };

});