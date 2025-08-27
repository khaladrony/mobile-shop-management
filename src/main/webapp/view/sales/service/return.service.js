app.service("ReturnService", function($q, Communication) {
    var service = {};

    service.transaction = null;
    service.returnMaster = { items: [] };

    // ----- RETURN OPERATIONS START-----

    // Search transaction
    service.fetchTransaction = function (transactionId) {
        var deferred = $q.defer();

        Communication.request("GET", API.POS_GET_BY_TRANSACTION_ID + '/' + transactionId, {})
            .then(function (resp) {
                if (resp.code === 200) {
                    service.transaction = resp.body;
                    deferred.resolve(resp.body);
                } else {
                    deferred.reject(resp.message || "Failed to fetch transaction");
                }
            }, function (err) {
                deferred.reject(err.message || "Server error");
            });

        return deferred.promise;
    };

    // Clear form data
    service.clearForm = function() {
        service.transaction = null;
        service.returnMaster = { items: [] };
    };

    // Validate return qty
    service.updateRefund = function(item) {
        if (item.returnQty > item.qty) {
            item.returnQty = item.qty;
        }
    };

    // Get refund total
    service.getTotalRefund = function () {
        if (!service.transaction) return 0;
        return service.transaction.items.reduce(function (sum, item) {
            var returnQty = parseFloat(item.returnQty) || 0;
            var price = parseFloat(item.price) || 0;
            return sum + (returnQty * price);
        }, 0);
    };

    // Build return master
    service.buildReturnMaster = function(transaction, payment, discount, remarks) {
        if (!transaction || !transaction.items) return;

        service.returnMaster = {
            id: null,
            transactionId: null,
            transactionDate: new Date(), // current date + time
            referenceNo: transaction.transactionId,
            transactionType: SALES_KEY.TRANSACTION_TYPE.POS,
            grossAmount: service.getTotalRefund(),
            netAmount: service.getTotalRefund(),
            discountType: discount?.type || SALES_KEY.DISCOUNT_TYPE.PERCENT,
            discountValue: discount?.value || 0,
            vatAmount: 0,
            totalAmount: service.getTotalRefund(),
            givenAmount: payment?.givenAmount || 0,
            returnAmount: 0,
            paymentType: payment?.type || SALES_KEY.PAYMENT_TYPE.CASH,
            customerName: transaction.customerName,
            mobileNumber: transaction.mobileNumber,
            remarks: remarks,
            status: 'RETURNED',
            items: transaction.items
                .filter(item => (parseFloat(item.returnQty) || 0) > 0)
                .map(item => ({
                    itemMasterId: item.itemMasterId,
                    itemCode: item.itemCode,
                    itemName: item.itemName,
                    quantity: parseFloat(item.returnQty),
                    price: parseFloat(item.price),
                    total: parseFloat(item.returnQty) * parseFloat(item.price)
                }))
        };

        return service.returnMaster;
    };

    // Process return
    service.processReturn = function(transaction, payment, discount, remarks) {
        var returnMaster = service.buildReturnMaster(transaction, payment, discount, remarks);

        return Communication.request("POST", API.POS_SAVE, returnMaster)
            .then(function (resp) {
                if (resp.code === 200) {
                    service.clearForm();
                    return { success: true, message: "Return successful" };
                } else {
                    return $q.reject(resp.message);
                }
            }, function (err) {
                return $q.reject(err.message);
            });
    };

    // ----- RETURN OPERATIONS END-----

    return service;
});
