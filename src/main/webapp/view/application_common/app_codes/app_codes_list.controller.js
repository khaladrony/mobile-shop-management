app.controller('AppCodesListCtrl', function ($scope, $http, $state, $timeout, $rootScope, $mdDialog, $interval, DialogBox, Communication) {
    $rootScope.setPageName(JMODULE_NAME,$state.current.name);

    $scope.search = {
        xtype: "",
        xcodes: ""
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

        var url = API.APP_CODES_FILTER + '?page=' + (currentPage - 1) + '&size=' + itemPerPage;

        DialogBox.showProgress();
        log("search obj: " + JSON.stringify($scope.search));
        var req = Communication.request("POST", url, $scope.search);
        req.then(function (resp) {
            DialogBox.hideProgress();
            log("user list: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.data.items = resp.body.content;
                $scope.data.itemCount = resp.body.totalElements;
            } else{
                $rootScope.toastError(resp.message);
            }

        }, function (err) {
            DialogBox.hideProgress();
            log("user list error", JSON.stringify(err));
        });
    };

    $scope.showEditForm = function (obj) {

        $state.go(JCOMPONENT.app_codes_update_view, {id: obj.id});
    };

});