app.controller("PosReportFormCtrl", function(
            $scope, $http, $state, $timeout, $rootScope, $mdDialog, $interval,
            DialogBox, Communication, ReportService
            ) {

    $scope.reportDate = new Date();
    $scope.salesReport = [];

    $scope.loadReport = function() {
        let formattedDate = $scope.reportDate.toISOString().split("T")[0];

        ReportService.fetchDailySales(formattedDate)
            .then(function(data) {
                $scope.salesReport = data;
            }).catch(function(err) {
                alert("Error: " + err);
            });
    };

    $scope.getTotalInvoices = function () {
        return $scope.salesReport.reduce((sum, r) => sum + r.numberOfInvoices, 0);
    };

    $scope.getTotalQuantity = function () {
        return $scope.salesReport.reduce((sum, r) => sum + r.totalQuantity, 0);
    };

    $scope.getTotalSales = function () {
        return $scope.salesReport.reduce((sum, r) => sum + r.totalSales, 0);
    };


    // Load today's report by default
    $scope.loadReport();

    $scope.exportExcel = function() {
        let formattedDate = $scope.reportDate.toISOString().split("T")[0];
        window.open(API.REPORT_DAILY_SALES + "/excel?date=" + formattedDate, "_blank");
    };

    $scope.exportPdf = function() {
        let formattedDate = $scope.reportDate.toISOString().split("T")[0];
        window.open(API.REPORT_DAILY_SALES + "/pdf?date=" + formattedDate, "_blank");
    };

    /*Report sales by item*/

    $scope.salesByItem = [];
    $scope.fromDate = new Date();
    $scope.toDate = new Date();

    $scope.loadSalesByItem = function() {
        let today = new Date().toISOString().slice(0, 10); // yyyy-MM-dd
        ReportService.fetchSalesByItem(today, today)
            .then(function(response) {
                $scope.salesByItem = response;
            });
    };

    $scope.getTotalItemQuantity = function () {
        return $scope.salesByItem.reduce(function (sum, item) {
            return sum + (item.totalQuantity || 0);
        }, 0);
    };

    $scope.getTotalItemSales = function () {
        return $scope.salesByItem.reduce(function (sum, item) {
            return sum + (item.totalSales || 0);
        }, 0);
    };

    $scope.loadSalesByItem();

    $scope.exportExcelSalesByItem = function() {
        let fromDate = $scope.fromDate.toISOString().split("T")[0];
        let toDate = $scope.toDate.toISOString().split("T")[0];
        window.open(API.REPORT_SALES_BY_ITEM + "/excel?startDate=" + fromDate+"&endDate="+toDate, "_blank");
    };

    $scope.exportPdfSalesByItem = function() {
        let fromDate = $scope.fromDate.toISOString().split("T")[0];
        let toDate = $scope.toDate.toISOString().split("T")[0];
        window.open(API.REPORT_SALES_BY_ITEM + "/pdf?startDate=" + fromDate+"&endDate="+toDate, "_blank");
    };
});