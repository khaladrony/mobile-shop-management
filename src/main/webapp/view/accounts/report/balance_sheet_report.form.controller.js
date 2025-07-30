app.controller('AccReportBalanceSheetFormCtrl', function ($scope, $http, $state, $timeout, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService, DialogBox, Communication, growl) {
    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;

    $scope.search = {
        asOnDate: ""
    };

    $scope.formFieldValidation = function () {
//        var asOnDate = new Date($scope.search.asOnDate);

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

        const formattedDate = formatDateLocal($scope.search.asOnDate);

        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", API.ACC_REPORT_BALANCE_SHEET + '/' + formattedDate , true);
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
        xhttp.send();
    };

    function formatDateLocal(date) {
      const d = new Date(date);
      const day = String(d.getDate()).padStart(2, '0');
      const month = String(d.getMonth() + 1).padStart(2, '0');
      const year = d.getFullYear();
      return `${year}-${month}-${day}`; // or `${day}-${month}-${year}` if needed
    }
});