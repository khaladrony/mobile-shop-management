app.controller('BankInfoListCtrl', function ($scope, $http, $state, $timeout, $rootScope, $mdDialog, $interval, DialogBox, Communication) {
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
        var req = Communication.request("POST", API.BANK_INFO_FILTER + "/" + currentPage + "/" + itemPerPage, $scope.search);
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

        $state.go(JCOMPONENT.bank_info_update_view, {id: obj.id});
    };

});