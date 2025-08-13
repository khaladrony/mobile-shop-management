app.controller('AppCodesFormCtrl', function (
            $scope, $http, $state, $timeout, $stateParams, $rootScope,
            $sce, $mdDialog, $interval, ClientService, DialogBox,
            encrypt, Communication, growl
            ) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;
    $scope.currentPage = 0;
    $scope.itemPerPage = 2000;
    $scope.search = {};
    $scope.module = {
        id: "",
        xtype: "",
        xcode: "",
        description: "",
        active: true
    };

    if($state.current.name === JCOMPONENT.app_codes_update_view){
        var req = Communication.request("GET", API.APP_CODES_GET + '/' + $stateParams.id, $scope.module);
        req.then(function (resp) {
            log("Code edit: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;
            }
        }, function (err) {
            log("Code edit error", JSON.stringify(err));
        });
    }

    $scope.saveModule = function () {
        var req;

        if($state.current.name === JCOMPONENT.app_codes_update_view){
            req = Communication.request("PUT", API.APP_CODES_UPDATE, $scope.module);
        } else{
            req = Communication.request("POST", API.APP_CODES_SAVE, $scope.module);
        }

        req.then(function (resp) {
            log("App codes: " + JSON.stringify(resp));
            if (resp.code === 200) {
                $scope.module = resp.body;
                growl.success('Successfully saved',{title: 'Success!'});
                $scope.resetModule();
                $scope.getDataList($scope.currentPage, $scope.itemPerPage);
            } else{
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            log("user error", JSON.stringify(err));
            $rootScope.toastError(err.message);
        });
    };

    $scope.reset = function() {};

    $scope.getDataList = function () {
        $scope._NG_SRC_ = _NG_SRC_;
        var url = API.APP_CODES_FILTER + '?page=' + ($scope.currentPage) + '&size=' + $scope.itemPerPage;

        DialogBox.showProgress();
        var req = Communication.request("POST", url, $scope.search);
        req.then(function (resp) {
            DialogBox.hideProgress();

            if (resp.code === 200) {
                $scope.tree = $scope.buildXtypeXcodeTree(resp.body.content);
            } else{
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            DialogBox.hideProgress();
            log("user list error", JSON.stringify(err));
        });
    };

    $scope.buildXtypeXcodeTree = function(flatList) {
        const xtypeGroup = {};

        flatList.forEach(item => {
            const xtype = item.xtype;
            const xcode = item.xcode;

            // If xtype node doesn't exist, create it
            if (!xtypeGroup[xtype]) {
                xtypeGroup[xtype] = {
                    label: xtype,
                    collapsed: true,
                    children: {}
                };
            }

            // If xcode node under xtype doesn't exist, create it
            if (!xtypeGroup[xtype].children[xcode]) {
                xtypeGroup[xtype].children[xcode] = {
                    label: xcode,
                    collapsed: true,
                    children: []
                };
            }
        });

        // Convert inner xcode objects to arrays
        const finalTree = Object.values(xtypeGroup).map(xtypeNode => {
            xtypeNode.children = Object.values(xtypeNode.children);
            return xtypeNode;
        });

        return finalTree;
    };

    $scope.resetModule = function () {
         $scope.module = {
                id: "",
                xtype: "",
                xcode: "",
                description: "",
                active: true
            };
    }

});