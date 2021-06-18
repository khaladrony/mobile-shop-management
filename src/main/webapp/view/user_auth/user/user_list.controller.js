app.controller('UserListCtrl', function ($scope, $http, $state, $timeout, $rootScope, $mdDialog, $interval, DialogBox, Communication) {
    $rootScope.setPageName(JMODULE_NAME,$state.current.name);

    $scope.search = {
        first_name: "",
        last_name: "",
        emp_code: "",
        usremail: ""
    };

    //filtering table column
    $scope.orderByField = '';
    $scope.reverseSort = false;

    //pagination varialbes
    $scope.data = {};
    $scope.data.items = [];
    $scope.data.itemCount = 0;
    $scope.currentPage = 1;
    $scope.itemPerPage = 10;
    $scope.inputRemarks = '';

    $scope.getDataList = function (currentPage, itemPerPage) {

        $scope.currentPage = currentPage;
        $scope.data = {};
        $scope.data.items = [];
        $scope.data.itemCount = 0;

        DialogBox.showProgress();
        log("search obj: " + JSON.stringify($scope.search));
        var req = Communication.request("POST", API.USER_FILTER + "/" + currentPage + "/" + itemPerPage, $scope.search);
        req.then(function (resp) {
            DialogBox.hideProgress();
            log("user list: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.data = resp.body;
            } else{
                $rootScope.toastError(resp.message);
            }

        }, function (err) {
            DialogBox.hideProgress();
            log("user list error", JSON.stringify(err));
        });
    };

    $scope.showEditForm = function (obj) {
        $state.go(JCOMPONENT.user_edit_view, {user_id: obj.user_id});
    };

    $scope.activeInactive = function (obj, val) {

        $("#showModal").modal("show");
        $scope.inputRemarks = obj.remarks;
        $scope.modalSaveClose = function (modalval) {
            if (modalval) {
                if (($scope.inputRemarks == null) || ($scope.inputRemarks.trim().length < 1 && !val)) {
                    $rootScope.toastError("You must add remarks!");
                    $("#showModal").modal("hide");
                    return;
                }

                obj.active = val;
                obj.remarks = $scope.inputRemarks;
                var req = Communication.request("PUT", API.USER_ACTIVE_INACTIVE + '/' + obj.user_id, obj);
                req.then(function (resp) {
                    log("user active/inactive: " + JSON.stringify(resp));
                    $scope.inputRemarks = '';
                    $("#showModal").modal("hide");
                    $scope.getDataList($scope.currentPage, $scope.itemPerPage);
                }, function (err) {
                    log("user error", JSON.stringify(err));
                });
            }
            $scope.inputRemarks = '';
        };

    };

    $scope.clearSession = function (obj, val) {
        DialogBox.confirm("Are you sure?").then(function(resp){
            if( resp===1 ){
                var req = Communication.request("PUT", API.CLEAR_SESSION, obj);
                req.then(function (res) {
                    if( res.code === 200 ){
                        $rootScope.toastSuccess("Session cleared from DB");
                        log("user active/inactive: " + JSON.stringify(res));
                        $scope.getDataList($scope.currentPage, $scope.itemPerPage);
                    }
                }, function (err) {
                    log("user error", JSON.stringify(err));
                });
            }
        });
    };

    $scope.downloadUserList = function (type, fileName) {

        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", API.ALL_USER_FILTER, true);
        xhttp.setRequestHeader('x-aip-token', _shskr_);
        xhttp.responseType = 'blob';
        xhttp.onload=function(e){
            if (this.status === 200) {
                var auditData = new Blob([this.response], {type: 'application/vnd.ms-excel'});
                var link = document.createElement('a');
                link.href = window.URL.createObjectURL(auditData);
                link.download = fileName;
                link.click();
            }
        };
        xhttp.send();
    };

});