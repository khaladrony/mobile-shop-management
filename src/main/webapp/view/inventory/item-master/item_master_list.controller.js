app.controller('ItemMasterListCtrl', function ($scope, $http, $state, $timeout, $rootScope, $mdDialog, $interval, DialogBox, Communication) {
    $rootScope.setPageName(JMODULE_NAME,$state.current.name);

    $scope.search = {
        item_code: "",
        item_name: ""
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

        $scope.data = {};
        $scope.data.items = [];
        $scope.data.itemCount = 0;
        $scope.currentPage = currentPage;

        var url = API.ITEM_MASTER_FILTER + '?page=' + (currentPage - 1) + '&size=' + itemPerPage;

        DialogBox.showProgress();
        log("search obj: " + JSON.stringify($scope.search));
        var req = Communication.request("POST", url, $scope.search);
        req.then(function (resp) {
            DialogBox.hideProgress();
            if (resp.code === 200) {
                $scope.data.items = resp.body.content;
                $scope.data.itemCount = resp.body.totalElements;
            } else{
                $rootScope.toastError(resp.message);
            }

        }, function (err) {
            DialogBox.hideProgress();
            log("Item list error", JSON.stringify(err));
        });
    };

    $scope.showEditForm = function (obj) {
        $state.go(JCOMPONENT.item_master_update_view, {id: obj.id});
    };

});