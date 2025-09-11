app.controller('PosTransactionFormCtrl', function (
            $scope, $http, $state, $timeout, $stateParams, $rootScope,
            $sce, $mdDialog, $interval, ClientService, DialogBox,
            encrypt, Communication, growl, DateHelperService, StoreService
            ) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;

    //filtering table column
    $scope.orderByField = '';
    $scope.reverseSort = false;

    //pagination varialbes
    $scope.data = {};
    $scope.data.items = [];
    $scope.data.itemCount = 0;
    $scope.currentPage = 1;
    $scope.itemPerPage = 10;
    $scope.search = {};
    $scope.categoryModal = {
        posCategory: ''
    };
    $scope.successMessage = '';

    $scope.module = {
        id: "",
        transactionDate: new Date(),
        type: 'EXPENSES',
        category: null,
        amount: "",
        referenceId: "",
        remarks: ""
    };

    $scope.saveModule = function () {
        if(!$scope.saveValidation()) {
            return;
        }

        $scope.module.transactionDate = DateHelperService.formatToLocalDateTimeString($scope.module.transactionDate);

        const isUpdate = $state.current.name === JCOMPONENT.pos_transaction_update_view && $scope.originalRef;

        var req;
        if(isUpdate){
            // apply changes back to the table row
            angular.extend($scope.originalRef, $scope.module);
            req = Communication.request("PUT", API.POS_TRANSACTION_UPDATE, $scope.module);
        } else{
            req = Communication.request("POST", API.POS_TRANSACTION_SAVE, $scope.module);
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
                    $state.go(JCOMPONENT.pos_transaction_add_view);
                }
            } else{
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            log("user error", JSON.stringify(err));
            $rootScope.toastError(err.message);
        }).finally(function () {
            if (typeof $scope.module.transactionDate === 'string') {
                $scope.module.transactionDate = new Date($scope.module.transactionDate);
            }
        });
    };

     $scope.saveValidation = function () {
         const validations = [
             { field: $scope.module.transactionDate, message: "Please enter date" },
             { field: $scope.module.type, message: "Please enter type" },
             { field: $scope.module.category, message: "Please enter category" },
             { field: $scope.module.amount, message: "Please enter amount" }
         ];

         for (let v of validations) {
             if (!v.field || v.field === "") {
                 growl.error(v.message, { title: "Error!" });
                 return false;
             }
         }

         return true;
     };

    $scope.addCategory = function () {
        Communication.request("POST", COMMON_API.app_codes_save, {
            "xtype": $scope.getType(),
            "xcode": $scope.categoryModal.posCategory
        }).then(function (resp) {
            if (resp.code === 200) {
                $scope.categoryModal.posCategory = '';
                $scope.focusCategoryInput();
                $scope.showSuccessMessage("Category added successfully!");

                // Reload the dropdown
                var dropdownScope = angular.element(document.getElementById('categoryDropdown')).isolateScope();
                if(dropdownScope && dropdownScope.reloadOptions){
                    dropdownScope.reloadOptions();
                }
            }
        }, function (err) {
            $rootScope.toastError(err.message);
        });
    };

    // Function to refocus the input
    $scope.focusCategoryInput = function () {
        setTimeout(() => {
            const input = document.getElementById('category');
            if (input) input.focus();
        }, 100);
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

    $scope.getType = function () {
        return $scope.module.type === 'EXPENSES'
            ? 'POS Expenses'
            : 'POS Income';
    };

    $scope.types = [
        { value: 'EXPENSES', label: 'Expenses' },
        { value: 'INCOME', label: 'Income' }
    ];

    $scope.originalRef = null; // holds reference to row object

    $scope.editRow = function (obj, $event) {
        $event.stopPropagation();

        StoreService.set("selectedTransaction", obj);
        $state.go(JCOMPONENT.pos_transaction_update_view);
        if($state.current.name === JCOMPONENT.pos_transaction_update_view){
            $scope.module = angular.copy(StoreService.get("selectedTransaction"));
            $scope.originalRef = StoreService.get("selectedTransaction");
            $scope.module.transactionDate = new Date($scope.module.transactionDate);
        }
    };

    if(StoreService.get("selectedTransaction") &&
            $state.current.name === JCOMPONENT.pos_transaction_update_view){
        $scope.module = angular.copy(StoreService.get("selectedTransaction"));
        $scope.originalRef = StoreService.get("selectedTransaction");
        $scope.module.transactionDate = new Date($scope.module.transactionDate);
    }

    $scope.getDataList = function (currentPage, itemPerPage) {
        $scope.data = {};
        $scope.data.items = [];
        $scope.data.itemCount = 0;

        var url = API.POS_TRANSACTION_FILTER + '?page=' + (currentPage - 1) + '&size=' + itemPerPage;

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

    $scope.openAddCategoryModal = function (){
        $('#categoryModal').modal('show');

        $('#categoryModal').on('shown.bs.modal', function () {
            $('#category').focus();
        });
    }

    $scope.resetModule = function () {
         $scope.module = {
             id: "",
             transactionDate: new Date(),
             type: 'EXPENSES',
             category: null,
             amount: "",
             referenceId: "",
             remarks: ""
         };
         $scope.originalRef = null;
    }

    $scope.init = function () {
        $scope.getDataList($scope.currentPage, $scope.itemPerPage);
    };

    $scope.init();
});