app.controller('BranchFormCtrl', function (
            $scope, $http, $state, $timeout, $stateParams, $rootScope,
            $sce, $mdDialog, $interval, ClientService, DialogBox,
            encrypt, Communication, growl, DateHelperService, StoreService
            ) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;

    //filtering table column
    $scope.orderByField = '';
    $scope.reverseSort = false;

    //pagination variables
    $scope.data = {};
    $scope.data.items = [];
    $scope.data.itemCount = 0;
    $scope.currentPage = 1;
    $scope.itemPerPage = 10;
    $scope.search = {};
    $scope.successMessage = '';

    $scope.module = {
        id: "",
        code: "",
        name: "",
        address: "",
        phone: "",
        managerName: "",
        active: true
    };

    $scope.saveModule = function () {
        if(!$scope.saveValidation()) {
            return;
        }

        const isUpdate = $state.current.name === JCOMPONENT.branch_update_view && $scope.originalRef;

        var req;
        if(isUpdate){
            // apply changes back to the table row
            angular.extend($scope.originalRef, $scope.module);
            req = Communication.request("PUT", API.BRANCH_UPDATE, $scope.module);
        } else{
            req = Communication.request("POST", API.BRANCH_SAVE, $scope.module);
        }

        req.then(function (resp) {
            if (resp.code === 200) {
                $scope.module = resp.body;
                $scope.resetModule();
                $scope.getDataList($scope.currentPage, $scope.itemPerPage);

                growl.success(
                    isUpdate ? "Successfully updated" : "Successfully saved",
                    { title: "Success!" }
                );

                if(isUpdate) {
                    $state.go(JCOMPONENT.branch_add_view);
                }
            } else{
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            log("user error", JSON.stringify(err));
            $rootScope.toastError(err.message);
        });
    };

     $scope.saveValidation = function () {
         const validations = [
             { field: $scope.module.code, message: "Please enter branch code" }
         ];

         for (let v of validations) {
             if (!v.field || v.field === "") {
                 growl.error(v.message, { title: "Error!" });
                 return false;
             }
         }

         return true;
     };

    // Function to show success message and auto-hide it
    $scope.showSuccessMessage = function (msg, duration = 3000) {
        $scope.successMessage = msg;
        $scope.$applyAsync(); // update the UI

        setTimeout(() => {
            $scope.successMessage = '';
            $scope.$apply();
        }, duration);
    };

    $scope.originalRef = null; // holds reference to row object

    $scope.editRow = function (obj, $event) {
        $event.stopPropagation();

        StoreService.set("selectedBranch", obj)
        $state.go(JCOMPONENT.branch_update_view);
        if($state.current.name === JCOMPONENT.branch_update_view){
            $scope.module = angular.copy(StoreService.get("selectedBranch"));
            $scope.originalRef = StoreService.get("selectedBranch");
        }
    };

    if(StoreService.get("selectedBranch") &&
            $state.current.name === JCOMPONENT.branch_update_view){
        $scope.module = angular.copy(StoreService.get("selectedBranch"));
        $scope.originalRef = StoreService.get("selectedBranch");
    }

    $scope.getDataList = function (currentPage, itemPerPage) {
        $scope.data = {};
        $scope.data.items = [];
        $scope.data.itemCount = 0;

        var url = API.BRANCH_FILTER + '?page=' + (currentPage - 1) + '&size=' + itemPerPage;

        DialogBox.showProgress();
        var req = Communication.request("POST", url, $scope.search);
        req.then(function (resp) {
            DialogBox.hideProgress();

           if (resp.code === 200) {
               $scope.data.items = resp.body.content;
               $scope.data.itemCount = resp.body.totalElements;
               $scope.currentPage = currentPage;
           } else{
               $rootScope.toastError(resp.message);
           }
        }, function (err) {
            DialogBox.hideProgress();
            log("user list error", JSON.stringify(err));
        });
    };

    $scope.resetModule = function () {
         $scope.module = {
            id: "",
            code: "",
            name: "",
            address: "",
            phone: "",
            managerName: "",
            active: true
         };
         $scope.originalRef = null;
    }

    $scope.init = function () {
        $scope.getDataList($scope.currentPage, $scope.itemPerPage);
    };

    $scope.init();
});