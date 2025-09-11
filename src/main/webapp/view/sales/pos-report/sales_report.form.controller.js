app.controller("PosReportFormCtrl", function(
            $scope, $http, $state, $timeout, $rootScope, $mdDialog, $interval,
            DialogBox, Communication, ReportService
            ) {

    $scope.reportFDate = new Date();
    $scope.reportTDate = new Date();
    $scope.salesReport = [];

    $scope.loadReport = function() {
        let fromDate = $scope.reportFDate.toISOString().split("T")[0];
        let toDate = $scope.reportTDate.toISOString().split("T")[0];

        ReportService.fetchDailySales(fromDate, toDate)
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

    $scope.getTotalGross = function () {
        return $scope.salesReport.reduce((sum, r) => sum + r.grossAmount, 0);
    };

    $scope.getTotalDiscount = function () {
        return $scope.salesReport.reduce((sum, r) => sum + r.discountValue, 0);
    };

    $scope.getTotalNetAmount = function () {
        return $scope.salesReport.reduce((sum, r) => sum + r.netAmount, 0);
    };

    $scope.getTotalVatAmount = function () {
        return $scope.salesReport.reduce((sum, r) => sum + r.vatAmount, 0);
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

    /* ===========SALES BY ITEM REPORT===========*/

    $scope.salesByItem = [];
    $scope.fromDate = new Date();
    $scope.toDate = new Date();

    $scope.loadSalesByItem = function() {
        let fromDate = $scope.fromDate.toISOString().split("T")[0];
        let toDate = $scope.toDate.toISOString().split("T")[0];
        ReportService.fetchSalesByItem(fromDate, toDate)
            .then(function(response) {
                $scope.salesByItem = response;
            });
    };

    $scope.loadSalesByItem();

    $scope.getTotalItemQuantity = function () {
        return $scope.salesByItem.reduce((sum, r) => sum + r.totalQuantity, 0);
    };

    $scope.getItemGross = function () {
        return $scope.salesByItem.reduce((sum, r) => sum + r.totalGrossAmount, 0);
    };

    $scope.getItemDiscount = function () {
        return $scope.salesByItem.reduce((sum, r) => sum + r.totalDiscountAmount, 0);
    };

    $scope.getItemNetAmount = function () {
        return $scope.salesByItem.reduce((sum, r) => sum + r.totalNetAmount, 0);
    };

    $scope.getItemVat = function () {
        return $scope.salesByItem.reduce((sum, r) => sum + r.totalVatAmount, 0);
    };

    $scope.getTotalItemSales = function () {
        return $scope.salesByItem.reduce((sum, r) => sum + r.totalAmount, 0);
    };

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

    /* ===========ALL ORDERS BY DATE REPORT===========*/

    $scope.fDate = new Date();
    $scope.tDate = new Date();

    $scope.loadOrdersByDate = function() {
        $scope.allOrders = [];
        let fromDate = $scope.fDate.toISOString().split("T")[0];
        let toDate = $scope.tDate.toISOString().split("T")[0];
        ReportService.fetchOrdersByDate(fromDate, toDate)
            .then(function(response) {
                $scope.allOrders = response;
                console.log(JSON.stringify(response));
            });
    };

    $scope.loadOrdersByDate();

     $scope.getOrdersQty = function () {
        return $scope.allOrders.reduce((sum, r) => sum + r.quantity, 0);
    };

    $scope.getOrdersGross = function () {
        return $scope.allOrders.reduce((sum, r) => sum + r.grossAmount, 0);
    };

    $scope.getOrdersDiscount = function () {
        return $scope.allOrders.reduce((sum, r) => sum + r.discountAmount, 0);
    };

    $scope.getOrdersNetAmount = function () {
        return $scope.allOrders.reduce((sum, r) => sum + r.netAmount, 0);
    };

    $scope.getOrdersVatAmount = function () {
        return $scope.allOrders.reduce((sum, r) => sum + r.vatAmount, 0);
    };

    $scope.getOrdersTotalAmount = function () {
        return $scope.allOrders.reduce((sum, r) => sum + r.totalAmount, 0);
    };

    /* ===========SALES PROFIT REPORT===========*/

    $scope.pFromDate = new Date();
    $scope.pToDate = new Date();

    $scope.loadSalesProfitByDate = function() {
        $scope.salesProfit = {};
        let fromDate = $scope.pFromDate.toISOString().split("T")[0];
        let toDate = $scope.pToDate.toISOString().split("T")[0];
        ReportService.fetchSalesProfitByDate(fromDate, toDate)
            .then(function(response) {
                $scope.salesProfit = response;
                console.log(JSON.stringify(response));
            });
    };

    $scope.loadSalesProfitByDate();


    /* ===========STOCK SUMMARY REPORT===========*/

    $scope.fStockDate = new Date();
    $scope.tStockDate = new Date();

    $scope.loadStockSummaryByDate = function() {
        $scope.stockSummary = {};
        let fromDate = $scope.fStockDate.toISOString().split("T")[0];
        let toDate = $scope.tStockDate.toISOString().split("T")[0];
        ReportService.fetchStockSummaryByDate(fromDate, toDate)
            .then(function(response) {
                $scope.stockSummary = response;
                console.log(JSON.stringify(response));
            });
    };

    $scope.loadStockSummaryByDate();
});