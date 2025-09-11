app.controller('ItemMasterFormCtrl', function ($scope, $http, $state, $timeout,
                $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService,
                DialogBox, encrypt, Communication, growl, ItemService, ImageService
                ) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;
    $scope.module = {
        id: "",
        itemCode: "",
        itemName: "",
        description: "",
        category: null,
        brand: null,
        model: "",
        color: null,
        storage: "",
        unit: null,
        price: "",
        purchasePrice: "",
        standardPrice: "",
        standardCost: "",
        imei: "",
        active: true,
        fileName: "",
        trackingType: "NONE"
    };

    $scope.trackingTypes = [
      { key: 'NONE', value: 'NONE' },
      { key: 'IMEI', value: 'IMEI' },
      { key: 'SERIAL', value: 'Serial' }
    ];

    if($state.current.name === JCOMPONENT.item_master_update_view) {
        var req = Communication.request("GET", API.ITEM_MASTER_GET + '/' + $stateParams.id, $scope.module);
        req.then(function (resp) {
            log("Item master edit: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;
                $scope.imageUrl = ImageService.getImageUrl(JMODULE_NAME, $scope.module.fileName, API.IMAGE_FETCH);

            }
        }, function (err) {
            log("Item master edit error", JSON.stringify(err));
        });
    }

    $scope.saveModule = function () {
        if(!$scope.saveValidation()) {
            return;
        }

        var req;

        if($state.current.name === JCOMPONENT.item_master_update_view){
            req = Communication.request("PUT", API.ITEM_MASTER_UPDATE, $scope.module);
        } else{
            req = Communication.request("POST", API.ITEM_MASTER_SAVE, $scope.module);
        }

        req.then(function (resp) {
            log("user manage: " + JSON.stringify(resp));
            if (resp.code === 200) {
                growl.success('Successfully saved',{title: 'Success!'});
                $scope.module = resp.body;
                $rootScope.toastSuccess("Successfully saved");

                //Item cache clear
                ItemService.clearCache();

                $state.go(JCOMPONENT.item_master_list_view);
            } else{
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            log("user error", JSON.stringify(err));
            $rootScope.toastError(err.message);
        });
    };

     $scope.saveValidation = function () {
        if ($scope.module.itemName === '') {
            growl.error('Please enter item name',{title: 'Error!'});
            return false;
        }
        return true;
     };

     $scope.uploadFile = function() {
        ImageService.upload(JMODULE_NAME, $scope.myFile, API.IMAGE_UPLOAD)
            .then(function(response) {
                $scope.module.fileName = response.data.body; // JSON from backend
                $scope.imageUrl = ImageService.getImageUrl(JMODULE_NAME, $scope.module.fileName, API.IMAGE_FETCH);
            })
            .catch(function() {
                alert("Error uploading image");
            });
     };

    $scope.reset = function() {};
});