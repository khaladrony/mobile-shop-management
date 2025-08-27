app.service("ReportService", function($q, Communication) {
    var service = {};

    // Fetch Daily Sales Report
    service.dailySales = [];

    service.fetchDailySales = function(date) {
        var deferred = $q.defer();

        Communication.request("GET", API.REPORT_DAILY_SALES + "?date=" + encodeURIComponent(date))
            .then(function(resp) {
                if (resp.code === 200) {
                    service.dailySales = resp.body;
                    deferred.resolve(resp.body);
                } else {
                    deferred.reject(resp.message || "Failed to fetch daily sales report");
                }
            }, function(err) {
                deferred.reject(err.message || "Server error");
            });

        return deferred.promise;
    };


    // Fetch Sales By Item Report
    service.salesByItem = [];

    service.fetchSalesByItem = function(startDate, endDate) {
        var deferred = $q.defer();

        Communication.request("GET", API.REPORT_SALES_BY_ITEM + "?startDate=" + encodeURIComponent(startDate) + "&endDate=" + encodeURIComponent(endDate))
            .then(function(resp) {
                if (resp.code === 200) {
                    service.salesByItem = resp.body;
                    deferred.resolve(resp.body);
                } else {
                    deferred.reject(resp.message || "Failed to fetch sales by item report");
                }
            }, function(err) {
                deferred.reject(err.message || "Server error");
            });

        return deferred.promise;
    };

    return service;
});
