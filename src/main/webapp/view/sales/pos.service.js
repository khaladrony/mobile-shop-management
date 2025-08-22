app.factory('PosReturnService', function (
        $http, $q, DialogBox, ToastService, Communication, $timeout
        ) {
    var service = {};

    // Transaction + Return Master for return
    service.transaction = null;
    service.returnMaster = { items: [] };

     // Customer related
    service.order = { customer: null };
    service.customer = {};       // temporary modal object
    service.search = { string: "" };
    service.searchResults = [];
    service.showAll = false;

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
    service.buildReturnMaster = function(transaction, payment, discount) {
        if (!transaction || !transaction.items) return;

        service.returnMaster = {
            id: null,
            transactionId: null,
            transactionDate: new Date(),
            referenceNo: transaction.transactionId,
            transactionType: SALES_KEY.TRANSACTION_TYPE.POS,
            grossAmount: service.getTotalRefund(),
            netAmount: service.getTotalRefund(),
            discountType: discount?.type || SALES_KEY.DISCOUNT_TYPE.PERCENT,
            discountValue: 0,
            vatAmount: 0,
            totalAmount: service.getTotalRefund(),
            givenAmount: 0,
            returnAmount: 0,
            paymentType: payment?.type || SALES_KEY.PAYMENT_TYPE.CASH,
            customerName: transaction.customerName,
            mobileNumber: transaction.mobileNumber,
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
    service.processReturn = function(transaction, payment, discount) {
        var returnMaster = service.buildReturnMaster(transaction, payment, discount);

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

// ----- CUSTOMER OPERATIONS START-----

    // Clear customer form
    service.clearCustomerForm = function(form) {
        service.customer = {};
        service.search = { string: "" };
        service.searchResults = [];
        service.showAll = false;

        if (form) {
            service.customer.mobileNumber = "";
            form.$setPristine();
            form.$setUntouched();
        }
    };

    // Save new customer
    service.saveCustomer = function(customer, form) {
        var deferred = $q.defer();

        Communication.request("POST", COMMON_API.save_customer, customer)
            .then(function(res) {
                if (res.code === 200) {
                    service.order.customer = res.body;
                    service.customer = {};
                    if (form) {
                        form.$setPristine();
                        form.$setUntouched();
                    }
                    deferred.resolve(res.body);
                } else {
                    deferred.reject(res.message || "Error saving customer");
                }
            }, function(err) {
                deferred.reject(err.message || "Server error while saving customer");
            });

        return deferred.promise;
    };

    // Search customer
    service.searchCustomer = function(query) {
        if (!query) return $q.resolve([]);
        return Communication.request("GET", COMMON_API.search_customer + "?query=" + query)
            .then(function(res) {
                service.searchResults = res.body || [];
                service.showAll = false;
                return service.searchResults;
            });
    };

    // Toggle show all results
    service.toggleShowAll = function() {
        service.showAll = !service.showAll;
        return service.showAll;
    };

    // Select customer
    service.selectCustomer = function(cust) {
        service.customer = angular.copy(cust);
        service.order.customer = service.customer;
        service.searchResults = [];
        return service.customer;
    };

// ----- CUSTOMER OPERATIONS END-----

// ----- PAYMENT OPERATIONS START-----

    // ----- PAYMENT STATE -----
    service.payment = {
        type: SALES_KEY.PAYMENT_TYPE.CASH,
        givenAmount: 0
    };

    // ----- RESET PAYMENT -----
    service.resetPayment = function () {
        service.payment.type = SALES_KEY.PAYMENT_TYPE.CASH;
        service.payment.givenAmount = 0;
    };

    // ----- DISCOUNT STATE -----
    service.discount = {
        type: SALES_KEY.DISCOUNT_TYPE.PERCENT,  // 'percent' or 'amount'
        value: 0
    };

    // ----- RESET DISCOUNT -----
    service.resetDiscount = function () {
        service.discount.type = SALES_KEY.DISCOUNT_TYPE.PERCENT;
        service.discount.value = 0;
    };

    // ----- PROCESS PAYMENT -----
    service.process = function ($scope, status) {
        var deferred = $q.defer();

        var orderData = {
            items: $scope.orderItems,
            id: $scope.id,
            transactionId: $scope.transactionId,
            transactionDate: $scope.id != null ? $scope.transactionDate : new Date(),
            transactionType: SALES_KEY.TRANSACTION_TYPE.POS,
            grossAmount: $scope.grossAmount,
            netAmount: $scope.netAmount,
            discountType: $scope.discount.type,
            discountValue: $scope.discountAmount,
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

    // ----- CONFIRM PAYMENT -----
    service.confirm = function($scope) {
        if ($scope.payment.givenAmount < $scope.totalAmount) {
            $scope.showMessage('failed', 'Customer paid less than total!', 6000);
            return;
        }
        return service.process($scope, SALES_KEY.STATUS.COMPLETED);
    };

    // ----- HOLD ORDER -----
    service.hold = function($scope) {
        if ($scope.totalAmount > 0) {
            return service.process($scope, SALES_KEY.STATUS.HELD);
        } else {
            $scope.showMessage('failed', 'Cannot proceed — no items added.', 6000);
        }
    };


// ----- PAYMENT OPERATIONS START-----


// ----- ORDER LIST OPERATIONS START-----

// Pagination + data state
    service.data = {
        items: [],
        itemCount: 0
    };

    service.currentPage = 1;
    service.itemPerPage = 10;

    // Search filter
    service.searchFilter = {
        sortField: "transactionDate",
        sortDirection: "desc"
    };

    // Fetch order list
    service.getDataList = function(currentPage, itemPerPage) {
        service.currentPage = currentPage;
        service.itemPerPage = itemPerPage;

        service.data = { items: [], itemCount: 0 };

        var url = API.POS_FILTER + '?page=' + (currentPage - 1) + '&size=' + itemPerPage;

        DialogBox.showProgress();
        var req = Communication.request("POST", url, service.searchFilter);

        return req.then(function(resp) {
            DialogBox.hideProgress();

            if (resp.code === 200) {
                service.data.items = resp.body.content;
                service.data.itemCount = resp.body.totalElements;

                service.data.items.forEach(function(master) {
                    master.showDetails = false;
                });
            }

            return service.data;

        }, function(err) {
            DialogBox.hideProgress();
            log("POS order list fetching error", JSON.stringify(err));
            return $q.reject(err);
        });
    };

    // Toggle details in modal list
    service.toggleDetails = function(master) {
        master.showDetails = !master.showDetails;
    };

    // Currently selected order
    service.selectedOrder = {};

    // Select order + prepare items
    service.selectOrder = function(master) {
        service.selectedOrder = angular.copy(master);

        var orderItems = [];
        angular.forEach(master.items, function(item) {
            orderItems.push({
                id: item.id,
                name: item.itemName,
                price: item.price,
                quantity: item.quantity,
                total: item.total,
                itemMasterId: item.itemMasterId,
                itemCode: item.itemCode || item.itemName,
                itemName: item.itemName
            });
        });

        service.selectedOrder.orderItems = orderItems;
        service.selectedOrder.totalProducts = orderItems.length;

        return service.selectedOrder;
    };
// ----- ORDER LIST OPERATIONS END-----

    return service;

});