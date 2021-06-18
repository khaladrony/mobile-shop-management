app.controller('FormButtonPermissionCtrl', function($scope, $timeout, $interval, DialogBox, ClientService) {
    $scope.JMODULE_NAME = JMODULE_NAME;
    $scope.JCONTROLLER = JCONTROLLER;
    $scope.JCOMPONENT = JCOMPONENT;
    $scope.hasPermission = function(module, component){
        return ClientService.hasPermission(module, null, component);
    };
    
});