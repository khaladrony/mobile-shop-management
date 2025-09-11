app.controller("PosReceiptFormCtrl", function(
            $scope, $http, $state, $timeout, $rootScope, $mdDialog, $interval,
            DialogBox, Communication, ReportService
            ) {

    // Receipt-related settings
    $scope.compactMode = false;
    $scope.toggleCompact = function () {
        $scope.compactMode = !$scope.compactMode;
    };
    $scope.printReceipt = function () {
        window.print();
    };

    $scope.getReceiptData = function () {
        var id = 166;
        var req = Communication.request("GET", API.POS_RECEIPT + "/" + id);
        req.then(function (resp) {
            if (resp.code === 200) {
                let data = resp.body;
                let branch = data.branch;

                $scope.receiptData = data;

                // store info
                $scope.store = {
                    name: branch?.name || "Unknown Store",
                    address: branch?.address || "",
                    phone: branch?.phone || "",
                    footerNote: 'Powered by YourPOS — www.example.com'
                };

                // sale info
                $scope.sale = {
                    date: new Date(data.date),
                    cashier: data.cashier || "N/A",
                    txnNo: data.txnNo,
                    items: data.items || [],
                    discount: data.discountValue || 0,
                    vatRate: data.vatRate || 0,
                    serviceCharge: data.serviceCharge || 0,
                    paymentMethod: data.paymentMethod,
                    paid: data.paid || 0
                };
            }
        }, function (err) {
            log("receipt data fetch error", JSON.stringify(err));
        });
    };

    $scope.subtotal = function () {
        return $scope.receiptData.grossAmount;
    };

    $scope.vatAmount = function () {
        return $scope.receiptData.vatAmount;
    };

    $scope.grandTotal = function () {
        return $scope.receiptData.totalAmount;
    };

    $scope.change = function () {
        return $scope.receiptData.returnAmount;
    };

    $scope.init = function () {
        $scope.getReceiptData();
    };

    $scope.init();
});