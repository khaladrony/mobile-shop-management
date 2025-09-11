app.service("PaymentService", function($q, $rootScope, Communication, DateHelperService) {
    var service = this;

    // ----- PAYMENT STATE -----
    service.payment = {
        type: SALES_KEY.PAYMENT_TYPE.CASH,
        givenAmount: 0
    };

    // Reset payment
    service.resetPayment = function () {
        service.payment.type = SALES_KEY.PAYMENT_TYPE.CASH;
        service.payment.givenAmount = 0;
    };

    // ----- DISCOUNT STATE -----
    service.discount = {
        type: SALES_KEY.DISCOUNT_TYPE.PERCENT,  // 'percent' or 'amount'
        value: 0
    };

    service.openInvoiceTab = function(invoiceId) {
        var url = '#!/'+ JCOMPONENT.invoice_view + '/' + invoiceId;  // if using ngRoute
        window.open(url, '_blank');
    };

    // Reset discount
    service.resetDiscount = function () {
        service.discount.type = SALES_KEY.DISCOUNT_TYPE.PERCENT;
        service.discount.value = 0;
    };

    // Process payment
    service.process = function ($scope, status) {
        var deferred = $q.defer();

        var orderData = {
            items: $scope.orderItems,
            id: $scope.id,
            transactionId: $scope.transactionId,
            transactionDate: new Date(),
            transactionType: SALES_KEY.TRANSACTION_TYPE.POS,
            grossAmount: $scope.grossAmount,
            netAmount: $scope.netAmount,
            discountType: $scope.discount.type,
            discountValue: $scope.discount.value,
            vatAmount: $scope.vatAmount,
            totalAmount: $scope.totalAmount,
            givenAmount: $scope.payment.givenAmount,
            returnAmount: $scope.payment.givenAmount > 0
                ? $scope.payment.givenAmount - $scope.totalAmount
                : $scope.payment.givenAmount,
            paymentType: service.payment.type,
            customerName: $scope.order.customer ? $scope.order.customer.customerName : "",
            mobileNumber: $scope.order.customer ? $scope.order.customer.mobileNumber : "",
            status: status
        };

        var req = orderData.id
            ? Communication.request("PUT", API.POS_UPDATE, orderData)
            : Communication.request("POST", API.POS_SAVE, orderData);

        req.then(function(resp) {
            if (resp.code === 200) {
                deferred.resolve(resp.body);
                $scope.clearOrders();

                if (status === SALES_KEY.STATUS.COMPLETED) {
                    $scope.showMessage('success', 'Payment successful!', 3500);
                    service.openInvoiceTab(resp.body.id);
                } else {
                    $scope.showMessage('success', 'Order ' + status.toLowerCase() + ' successful!', 3500);
                }
            } else {
                $rootScope.toastError(resp.message);
                deferred.reject(resp.message);
            }
        }, function(err) {
            console.error("POS payment error", err);
            $rootScope.toastError(err.message);
            deferred.reject(err.message);
        });

        return deferred.promise;
    };

    // Confirm payment
    service.confirm = function($scope) {
        if ($scope.payment.givenAmount < $scope.totalAmount) {
            $scope.showMessage('failed', 'Customer paid less than total!', 6000);
            return;
        }
        return service.process($scope, SALES_KEY.STATUS.COMPLETED);
    };

    // Hold order
    service.hold = function($scope) {
        if ($scope.totalAmount > 0) {
            return service.process($scope, SALES_KEY.STATUS.HELD);
        } else {
            $scope.showMessage('failed', 'Cannot proceed — no items added.', 6000);
        }
    };
});
