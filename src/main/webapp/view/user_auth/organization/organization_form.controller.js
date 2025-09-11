app.controller('OrganizationFormCtrl', function (
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
    $scope.isUpdate = true;

    $scope.module = {
        id: "",
        name: "",
        title: "",
        shortName: "",
        address1: "",
        address2: "",
        webUrl: "",
        email: "",
        contactName: "",
        contactPhone: "",
        logo: "",
        active: true
    };

    $scope.saveModule = function () {
        if(!$scope.saveValidation()) {
            return;
        }

        var req;
        if($scope.isUpdate){
            req = Communication.request("PUT", API.ORGANIZATIONS_UPDATE, $scope.module);
        } else{
            req = Communication.request("POST", API.ORGANIZATIONS_SAVE, $scope.module);
        }

        req.then(function (resp) {
            if (resp.code === 200) {
                $scope.module = resp.body;

                growl.success(
                    $scope.isUpdate ? "Successfully updated" : "Successfully saved",
                    { title: "Success!" }
                );
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
             { field: $scope.module.name, message: "Please enter organization name" }
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

    $scope.getOrganization = function () {

        var url = API.ORGANIZATIONS_GET;

        DialogBox.showProgress();
        var req = Communication.request("GET", url, {});
        req.then(function (resp) {
            DialogBox.hideProgress();

           if (resp.code === 200) {
               $scope.module = resp.body;
           } else{
               $rootScope.toastError(resp.message);
           }
        }, function (err) {
            DialogBox.hideProgress();
            log("user list error", JSON.stringify(err));
        });
    };

    $scope.init = function () {
        $scope.getOrganization();
    };

    $scope.init();

});