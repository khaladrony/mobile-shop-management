app.controller("InvoiceFormCtrl", function($scope, $timeout, Communication, $stateParams) {

    $scope.getInvoiceData = function () {
        var id = $stateParams.invoiceId;

        var req = Communication.request("GET", API.POS_RECEIPT + "/" + id);
        req.then(function (resp) {
            if (resp.code === 200) {
                let data = resp.body;
                let branch = data.branch;

                $scope.invoiceData = data;

                // store info
                $scope.store = {
                    name: branch?.name || "Unknown Store",
                    address: branch?.address || "",
                    phone: branch?.phone || "",
                    footerNote: "Thank you for shopping with us!"
                };

                // sale info
                $scope.invoice = {
                    date: new Date(data.date),
                    txnNo: data.txnNo,
                    customerName: data.customerName,
                    customerPhone: data.mobileNumber,
                    items: data.items || [],
                    discount: data.discountValue || 0,
                    vatRate: data.vatRate || 0,
                    paymentMethod: data.paymentMethod,
                    paid: data.paid || 0
                };
            }
        }, function (err) {
            log("receipt data fetch error", JSON.stringify(err));
        });
    };

    // Totals
    $scope.subtotal = function () {
        return $scope.invoiceData.grossAmount;
    };

    $scope.vatAmount = function () {
        return $scope.invoiceData.vatAmount;
    };

    $scope.grandTotal = function () {
        return $scope.invoiceData.totalAmount;
    };

    $scope.change = function () {
        return $scope.invoiceData.returnAmount;
    };

    // Print handling
    $scope.printMode = "a4"; // default A4

    $scope.printInvoice = function () {
        $timeout(function () {
            window.print();
        }, 300);
    };

    $scope.togglePageSize = function () {
        $scope.printMode = ($scope.printMode === "a4") ? "a4-half" : "a4";
    };


    $scope.init = function () {
        $scope.getInvoiceData();
    };

    $scope.init();
});
