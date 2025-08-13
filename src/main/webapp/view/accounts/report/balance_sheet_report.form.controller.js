app.controller('AccReportBalanceSheetFormCtrl', function (
            $scope, $http, $state, $timeout, $stateParams, $rootScope, $sce,
            $mdDialog, $interval, ClientService, DialogBox, Communication, growl,
            DateHelperService, ReportPreviewService
            ) {
    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;

    $scope.search = {
        asOnDate: ""
    };

    $scope.formFieldValidation = function () {
        if ($scope.search.asOnDate === '') {
            growl.error('As on date required', {title: 'Error!'});
            return false;
        }

        return true;
    };

    $scope.balanceSheetPreview = function () {
        if(!$scope.formFieldValidation()){
            return;
        }

        const formattedDate = DateHelperService.formatDateLocal($scope.search.asOnDate);

        var reportUrl = API.ACC_REPORT_BALANCE_SHEET + '/' + formattedDate;
        var token = _shskr_;

        ReportPreviewService.previewPdf(reportUrl, token)
            .then(function () {
                console.log("PDF opened successfully.");
            })
            .catch(function (error) {
                console.error(error);
            });
    };
});