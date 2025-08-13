app.controller('AccReportTrialBalanceFormCtrl', function (
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

    $scope.trialBalancePreview = function () {
        if(!$scope.formFieldValidation()){
            return;
        }

        const formattedDate = DateHelperService.formatDateLocal($scope.search.asOnDate);

        var reportUrl = API.ACC_REPORT_TRIAL_BALANCE + '/' + formattedDate;
        var token = _shskr_;

        ReportPreviewService.previewPdf(reportUrl, token)
            .then(function () {
                console.log("PDF opened successfully.");
            })
            .catch(function (error) {
                console.error(error);
            });

        /*var xhttp = new XMLHttpRequest();
        xhttp.open("GET", API.ACC_REPORT_TRIAL_BALANCE + '/' + $scope.search.asOnDate , true);
        xhttp.setRequestHeader('x-aip-token', _shskr_);
        xhttp.responseType = 'blob';
        xhttp.onload = function (e) {
            if (this.status === 200) {
                var pdfResponse = new Blob([this.response], {type: 'application/pdf'});
                var fileURL = URL.createObjectURL(pdfResponse);
                var link = document.createElement('a');
                link.href = fileURL;
                link.target = '_blank';
                link.click();
            }
        };
        xhttp.send();*/
    };



});