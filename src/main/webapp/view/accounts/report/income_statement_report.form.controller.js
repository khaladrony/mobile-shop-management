app.controller('AccReportIncomeStatementFormCtrl', function (
                $scope, $http, $state, $timeout, $stateParams, $rootScope, $sce,
                $mdDialog, $interval, ClientService, DialogBox, Communication, growl,
                DateHelperService, ReportPreviewService
                ) {
    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;

    $scope.search = {
        fromDate: "",
        toDate: ""
    };

    $scope.formFieldValidation = function () {
        if ($scope.search.fromDate === '') {
            growl.error('From date required', {title: 'Error!'});
            return false;
        }

        if ($scope.search.toDate === '') {
            growl.error('To date required', {title: 'Error!'});
            return false;
        }

        return true;
    };

    $scope.incomeStatementPreview = function () {
        if(!$scope.formFieldValidation()){
            return;
        }

        const result = DateHelperService.validateAndFormat(
                        $scope.search.fromDate,
                        $scope.search.toDate
                        );
        if (!result) return;

        const fromDate = DateHelperService.formatDateLocal($scope.search.fromDate);
        const toDate = DateHelperService.formatDateLocal($scope.search.toDate);

        var reportUrl = API.ACC_REPORT_INCOME_STATEMENT + '/' + fromDate + '/' + toDate;
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