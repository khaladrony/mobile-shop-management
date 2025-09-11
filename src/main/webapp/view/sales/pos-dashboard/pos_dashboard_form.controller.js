app.controller("PosDashboardFormCtrl", function(
            $scope, $http, $state, $timeout, $rootScope, $mdDialog, $interval,
            DialogBox, Communication
            ) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);

/*
    // Today summary
    $scope.todaySummary = [
        { title: "TODAY SOLD", value: 408919, class: "sold" },
        { title: "TODAY PURCHASE", value: 385053, class: "purchase" },
        { title: "TODAY EXPENSE", value: 4, class: "expense" },
        { title: "TODAY SELL PROFIT", value: 23865, class: "profit" }
    ];

    // Month summary
    $scope.monthSummary = [
        { title: "SOLD IN AUGUST 2025", value: 408919, class: "month-sold" },
        { title: "PURCHASED - IN AUGUST 2025", value: 33837066, class: "month-purchase" },
        { title: "EXPENSE IN AUGUST 2025", value: 4, class: "month-expense" },
        { title: "RETURNED IN AUGUST 2025", value: 9000, class: "month-return" },
        { title: "PROFIT MAY 2025", value: 23861, class: "month-profit" }
    ];*/

    // Daily summary
    Communication.request("GET", API.DAILY_SUMMARY).then(function (res) {
        $scope.todaySummary = res.body;
    });

    // Monthly summary
    Communication.request("GET", API.MONTHLY_SUMMARY).then(function (res) {
        $scope.monthSummary = res.body;
    });

});
