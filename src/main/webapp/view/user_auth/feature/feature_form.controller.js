app.controller('FeatureFormCtrl', function ($scope, $http, $state, $timeout, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService, DialogBox, Communication) {
    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.featureList = [];
    $scope.featureListFg = [];
    $scope.current_state = $state.current.name;
    $scope.typeList = [{"name":"Module"},{"name":"Feature Group"}, {"name":"Feature"}];

    $scope.showNeedPermission = 1;
    $scope.showModule = 1;
    $scope.showController = 1;
    $scope.showAction = 1;
    $scope.showComponent = 1;
    $scope.showParent = 0;
    $scope.showModuleOnly = 0;

    $scope.feature_parent_id = "";

    $scope.module = {
        feature_id: "",
        feature_name: "",
        feature_code: "",
        note: "",
        module: "",
        controler: "",
        action: "",
        component: "",
        type: "",
        url: "",
        is_menu: true,
        need_permission: true,
        sort_order: 7,
        parent_id: ""
    };






    $scope.saveModule = function () {
        var req;

        if ($scope.showParent === 1) {
            $scope.module.parent_id = $scope.feature_parent_id;
        }

        if ($state.current.name === JCOMPONENT.feature_update) {
            req = Communication.request("PUT", API.FEATURE_UPDATE + '/' + $scope.module.feature_id, $scope.module);
        } else {
            req = Communication.request("POST", API.FEATURE_SAVE, $scope.module);
        }

        req.then(function (resp) {
            log("Feature manage: " + JSON.stringify(resp));
            if (resp.code === 200) {
                $scope.module = resp.body;
                $rootScope.toastSuccess("Successfully saved");
                $state.go(JCOMPONENT.feature_list_view);
            } else {
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            log("Feature error", JSON.stringify(err));
        });
    };

    $scope.getFeatureList = function () {
        var req = Communication.request("GET", API.FEATURE_LIST, {});
        req.then(function (resp) {
            log("feature list: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.featureList = resp.body;
                $scope.featureListFg = resp.body;
            }

            if ($state.current.name === JCOMPONENT.feature_update) {
                $scope.getFeatureById();
            }

        }, function (err) {
            log("feature error", JSON.stringify(err));
        });
    };

    $scope.getFeatureById = function () {
        var req = Communication.request("GET", API.FEATURE_GET + '/' + $stateParams.feature_id, $scope.module);

        req.then(function (resp) {
            log("Feature edit: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;
                $scope.feature_parent_id = resp.body.parent_id;
                $scope.onChangeFeatureType(resp.body.type);
                log($scope.module.parent_id);
                log($scope.feature_parent_id);
            }

        }, function (err) {
            log("Feature edit error", JSON.stringify(err));
        });
    };

    $scope.onChangeFeatureType = function (type) {
        $scope.module.type = type;

        if (type.toLowerCase() === "module") {
            $scope.showModule = 0;
            $scope.showController = 0;
            $scope.showAction = 0;
            $scope.showComponent = 0;
            $scope.showParent = 0;
            $scope.showNeedPermission = 0;
            $scope.showModuleOnly = 0;

        } else if (type.toLowerCase() === "feature group") {
            $scope.showModule = 0;
            $scope.showController = 0;
            $scope.showAction = 0;
            $scope.showComponent = 0;
            $scope.showParent = 0;
            $scope.showModuleOnly = 1;
            $scope.showNeedPermission = 0;

        } else if (type.toLowerCase() === "feature") {
            $scope.showModule = 1;
            $scope.showController = 1;
            $scope.showAction = 1;
            $scope.showComponent = 1;
            $scope.showParent = 1;
            $scope.showModuleOnly = 0;
            $scope.showNeedPermission = 1;
        }
    };

    $scope.updateValue = function (feature_id) {
        $scope.feature_parent_id = feature_id;
        $scope.module.parent_id = feature_id;
        log("feature_id: " + feature_id);
    };
});