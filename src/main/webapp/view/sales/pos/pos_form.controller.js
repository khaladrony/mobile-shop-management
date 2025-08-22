app.controller('PosFormCtrl', function ($scope, $http, $state, $timeout,
                $q, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService,
                DialogBox, encrypt, Communication,$filter,growl, ItemService,
                ToasterMessageQueueService, DateHelperService, CustomerService,
                PosReturnService, NotificationService) {

    // Initialize data
    $scope.orderItems = [];
    $scope.menuItems = {
        coffee: [
            {id: 15, name: 'Espresso', price: 300.00, image: _STATIC_RES_ + '/images/product/coffee_01.jpg'},
            {id: 16, name: 'Cappuccino', price: 450.00, image: '/res/images/product/coffee_01.jpg'},
            {id: 17, name: 'Double Espresso', price: 350.00, image: '/res/images/product/coffee_01.jpg'},
            {id: 18, name: 'Americaon', price: 500.00, image: '/res/images/product/coffee_01.jpg'},
            {id: 19, name: 'Flat White', price: 400.00, image: '/res/images/product/coffee_01.jpg'},
            {id: 20, name: 'Ice Coffee', price: 200.00, image: '/res/images/product/coffee_01.jpg'}
        ],
        milkTea: [
            {id: 21, name: 'Milk Tea Classic', price: 100.00, image: _STATIC_RES_ + '/images/product/milktea_01.jpeg'},
            {id: 22, name: 'Pearl Milk Tea', price: 150.00, image: _STATIC_RES_ + '/images/product/milktea_02.jpeg'}
        ],
        fruitTea: [
            {id: 23, name: 'Black Tea', price: 50.00, image: _STATIC_RES_ + '/images/product/fruittea_01.jpeg'},
            {id: 24, name: 'Peach Tea', price: 70.00, image: _STATIC_RES_ + '/images/product/fruittea_02.jpeg'},
            {id: 25, name: 'Lemon Tea', price: 20.00, image: _STATIC_RES_ + '/images/product/fruittea_01.jpeg'},
            {id: 26, name: 'Winter Melon Tea', price: 100.00, image: _STATIC_RES_ + '/images/product/fruittea_02.jpeg'}
        ]
    };

    $scope.activeTab = 'coffee';
    $scope.searchQuery = '';
    $scope.vatRate = 0.1; // 10% VAT
    $scope.id = null;
    $scope.mobilePattern = /^\d{11}$/;

    // Add item to order
    $scope.addOrder = function(item) {
        var existingItem = $scope.orderItems.find(function(orderItem) {
            return orderItem.itemMasterId === item.id;
        });

        if (existingItem) {
            existingItem.quantity += 1;
        } else {
            $scope.orderItems.push({
//                id: item.id,
                name: item.name,
                price: item.price,
                quantity: 1,
                total: item.price,
                itemMasterId: item.id,
                itemCode: item.name,
                itemName: item.name
            });
        }
        $scope.calculateTotals();
    };

    // Remove item from order
    $scope.removeItem = function(index) {
        $scope.orderItems.splice(index, 1);
        $scope.calculateTotals();
    };

    // Update quantity
    $scope.updateQuantity = function(item, change) {
        item.quantity += change;
        if (item.quantity <= 0) {
            var index = $scope.orderItems.indexOf(item);
            $scope.orderItems.splice(index, 1);
        }
        $scope.calculateTotals();
    };

    // Calculate totals
    $scope.calculateTotals = function() {
        $scope.grossAmount = $scope.orderItems.reduce(function(sum, item) {
            item.total = item.price * item.quantity;
            return sum + item.total;
        }, 0);

        if ($scope.discount.type === SALES_KEY.DISCOUNT_TYPE.PERCENT) {
            $scope.discountAmount = ($scope.grossAmount * $scope.discount.value) / 100;
        } else {
            $scope.discountAmount = $scope.discount.value;
        }

        $scope.netAmount = $scope.grossAmount - $scope.discountAmount;
        $scope.vatAmount = $scope.netAmount * $scope.vatRate;
        $scope.totalAmount = $scope.netAmount + $scope.vatAmount;
        $scope.totalProducts = $scope.orderItems.length;
    };

    // Set active tab
    $scope.setActiveTab = function(tab) {
        $scope.activeTab = tab;
    };

    // Filter items by search query
    $scope.filteredItems = function(items) {
        if (!$scope.searchQuery) return items;
        return items.filter(function(item) {
            return item.name.toLowerCase().includes($scope.searchQuery.toLowerCase());
        });
    };

//  Payment modal
    $scope.payment = PosReturnService.payment;
    $scope.discount = PosReturnService.discount;

    $scope.openPaymentModal = function () {
        if ($scope.totalAmount > 0) {
            // Initialize/reset payment before showing modal
            PosReturnService.resetPayment();

            // Show modal
            $('#paymentModal').modal('show');
        } else {
            $scope.showMessage('failed', 'No items added to checkout.', 6000);
        }
    };

    $scope.confirmPayment = function () {
        PosReturnService.confirm($scope).then(function() {
            $('#paymentModal').modal('hide');
        });
    };

    $scope.holdOrder = function () {
        PosReturnService.hold($scope);
    };

    // Reset payment whenever modal is closed
    $('#paymentModal').on('hidden.bs.modal', function () {
        $scope.$apply(function () {
            PosReturnService.resetPayment();
        });
    });

    // Clear orders
    $scope.clearOrders = function() {
        $scope.id = null;

        // Reset via services
        PosReturnService.resetPayment();
        PosReturnService.resetDiscount();

        $scope.orderItems = [];
        $scope.calculateTotals();

        $scope.clearCustomerForm();
        PosReturnService.order.customer = {};
    };


/*
    $scope.openPaymentModal = function () {
        if ($scope.totalAmount > 0) {
            $('#paymentModal').modal('show');
        } else {
            $scope.showMessage('failed', 'No items added to checkout.', 6000);
        }
    };

    //Calculate discount
    $scope.discount = {
        type: SALES_KEY.DISCOUNT_TYPE.PERCENT, // 'percent' or 'amount'
        value: 0
    };

    //popup
    $scope.payment = {
        type: SALES_KEY.PAYMENT_TYPE.CASH,
        givenAmount: 0
    };

    // Process payment
    $scope.processPayment = function(status) {
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
            returnAmount: $scope.payment.givenAmount > 0 ? $scope.payment.givenAmount - $scope.totalAmount : $scope.payment.givenAmount,
            paymentType: $scope.payment.type,
            customerName: $scope.order.customer!=null ? $scope.order.customer.customerName : "",
            mobileNumber: $scope.order.customer!=null ? $scope.order.customer.mobileNumber : "",
            status: status
        };

        var req;
        if($scope.id != null) {
            req = Communication.request("PUT", API.POS_UPDATE, orderData);
        } else{
          req = Communication.request("POST", API.POS_SAVE, orderData);
        }

        req.then(function (resp) {
            if (resp.code === 200) {

                $scope.clearOrders();

                if(status === SALES_KEY.STATUS.COMPLETED){
                    $scope.showMessage('success', 'Payment successful!', 3500);
                } else{
                    $scope.showMessage('success', 'Order ' + status.toLowerCase() + ' successful!', 3500);
                }

            } else{
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            log("POS save error", JSON.stringify(err));
            $rootScope.toastError(err.message);
        });
    };

    // Clear orders
    $scope.clearOrders = function() {
        $scope.id = null;
        $scope.resetDiscount();
        $scope.resetPayment();
        $scope.orderItems = [];
        $scope.calculateTotals();
        $scope.clearCustomerForm();
        $scope.order.customer = {};
    };

    $scope.resetPayment = function() {
        $scope.payment = {
            type: SALES_KEY.PAYMENT_TYPE.CASH,
            givenAmount: 0
        };
    };

    $scope.resetDiscount = function() {
        $scope.discount = {
            type: SALES_KEY.DISCOUNT_TYPE.PERCENT,
            value: 0
        };
    };


    $scope.confirmPayment = function () {
        if ($scope.payment.givenAmount < $scope.totalAmount) {
            $scope.showMessage('failed', 'Customer paid less than total!', 6000);
            return;
        }

        $scope.processPayment(SALES_KEY.STATUS.COMPLETED);

        // Close modal
        $('#paymentModal').modal('hide');
    };

    $('#paymentModal').on('hidden.bs.modal', function () {
        var scope = angular.element(this).scope();
        scope.$apply(function () {
            scope.payment.type = SALES_KEY.PAYMENT_TYPE.CASH;
            scope.payment.givenAmount = 0;
        });
    });

    // Hold order
    $scope.holdOrder = function() {
        if ($scope.totalAmount > 0) {
           $scope.processPayment(SALES_KEY.STATUS.HELD);
        } else {
            $scope.showMessage('failed', 'Cannot proceed — no items added.', 6000);
        }
    };*/

    // Load menu items from backend (optional)
    $scope.loadMenuItems = function() {
        $http.get('/api/menu-items')
            .then(function(response) {
                $scope.menuItems = response.data;
            })
            .catch(function(error) {
                console.log('Using default menu items');
            });
    };

//  Toaster message
    $scope.messages = NotificationService.messages;

    $scope.showMessage = function(type, text, duration) {
        NotificationService.showMessage(type, text, duration);
    };

    $scope.closeMessage = function(i) {
        NotificationService.closeMessage(i);
    };


// Order list modal

    $scope.data = PosReturnService.data;
    $scope.currentPage = PosReturnService.currentPage;
    $scope.itemPerPage = PosReturnService.itemPerPage;
    $scope.selectedOrder = PosReturnService.selectedOrder;

    $scope.getDataList = function (page, size) {
        PosReturnService.getDataList(page, size).then(function(data) {
            $scope.data = data;
        });
    };

    $scope.toggleDetails = PosReturnService.toggleDetails;

    $scope.openTransactionModal = function () {
        $('#transactionModal').modal('show');
        $scope.getDataList($scope.currentPage, $scope.itemPerPage);
    };

    $scope.selectOrder = function(master) {
        let order = PosReturnService.selectOrder(master);

        // bind to scope if needed
        $scope.id = order.id;
        $scope.transactionId = order.transactionId;
        $scope.transactionDate = order.transactionDate;
        $scope.grossAmount = order.grossAmount;
        $scope.netAmount = order.netAmount;
        $scope.discount.type = order.discountType;
        $scope.discount.value = order.discountValue;
        $scope.vatAmount = order.vatAmount;
        $scope.totalAmount = order.totalAmount;
        $scope.payment.givenAmount = order.givenAmount;
        $scope.payment.type = order.paymentType;
        $scope.orderItems = order.orderItems;
        $scope.totalProducts = order.totalProducts;
        $scope.customerName = order.customerName;
        $scope.mobileNumber = order.mobileNumber;

        PosReturnService.order.customer = {
            customerName : order.customerName,
            mobileNumber : order.mobileNumber
        }

        $('#transactionModal').modal('hide');
    };



/*    //pagination variables
    $scope.data = {};
    $scope.data.items = [];
    $scope.data.itemCount = 0;
    $scope.currentPage = 1;
    $scope.itemPerPage = 10;

    $scope.getDataList = function (currentPage, itemPerPage) {
        $scope.search = {
                sortField: "transactionDate",
                sortDirection: "desc"
            };
        $scope.currentPage = currentPage;
        $scope.data = {};
        $scope.data.items = [];
        $scope.data.itemCount = 0;

        var url = API.POS_FILTER + '?page=' + (currentPage - 1) + '&size=' + itemPerPage;

        DialogBox.showProgress();
        var req = Communication.request("POST", url, $scope.search);
        req.then(function (resp) {
            DialogBox.hideProgress();

            if (resp.code === 200) {
                $scope.data.items = resp.body.content;
                $scope.data.itemCount = resp.body.totalElements;

                $scope.data.items.forEach(function (master) {
                    master.showDetails = false;
                });
            }

        }, function (err) {
            log("POS order list fetching error", JSON.stringify(err));
        });
    };

    $scope.toggleDetails = function (master) {
        master.showDetails = !master.showDetails;
    };

    $scope.openTransactionModal = function () {
        $('#transactionModal').modal('show');
        $scope.getDataList($scope.currentPage, $scope.itemPerPage);
    };

    $scope.selectedOrder = {}; // this will hold chosen transaction

    $scope.selectOrder = function(master) {
        $scope.selectedOrder = angular.copy(master);

        $scope.id = master.id;
        $scope.transactionId = master.transactionId;
        $scope.transactionDate = master.transactionDate;
        $scope.grossAmount = master.grossAmount;
        $scope.netAmount =  master.netAmount;
        $scope.discount.type =  master.discountType;
        $scope.discountAmount =  master.discountValue;
        $scope.vatAmount =  master.vatAmount;
        $scope.totalAmount = master.totalAmount;
        $scope.payment.givenAmount = master.givenAmount;
        $scope.payment.type = master.paymentType;

        $scope.orderItems = [];

        // loop through master.items and push into orderItems
        angular.forEach(master.items, function(item) {
            $scope.orderItems.push({
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

        $scope.totalProducts = $scope.orderItems.length;

        // close modal
        $('#transactionModal').modal('hide');
    };*/

    // Initialize
    $scope.calculateTotals();
    //    $scope.loadMenuItems();

//  Customer info modal

    // Bind service state directly to scope
    $scope.customer = PosReturnService.customer;
    $scope.search = PosReturnService.search;
    $scope.searchResults = PosReturnService.searchResults;
    $scope.showAll = PosReturnService.showAll;
    $scope.order = PosReturnService.order;

    // Modal open
    $scope.addCustomerModal = function () {
        if ($scope.totalProducts > 0) {
            $('#customerModal').modal('show');
        } else {
            $scope.showMessage('failed', 'Cannot proceed — no items added.', 6000);
        }
    };

    // Clear form
    $scope.clearCustomerForm = function(form) {
        PosReturnService.clearCustomerForm(form);
    };

    // Save customer
    $scope.saveCustomer = function(form) {
        if (form.$valid) {
            PosReturnService.saveCustomer($scope.customer, form)
                .then(function(cust) {
                    $('#customerModal').modal('hide');
                    $scope.showMessage('success', 'Customer saved successfully', 3500);
                })
                .catch(function(err) {
                    $scope.showMessage('failed', err, 6000);
                });
        }
    };

    // Search
    $scope.searchCustomer = function() {
        PosReturnService.searchCustomer($scope.search.string);
    };

    // Select
    $scope.selectCustomer = function(cust) {
        PosReturnService.selectCustomer(cust);
        $('#customerModal').modal('hide');
    };

    // Toggle
    $scope.toggleShowAll = function() {
        $scope.showAll = PosReturnService.toggleShowAll();
    };

    // Keep scope synced with service objects
    $scope.$watch(function() { return PosReturnService.customer; }, function(n) { $scope.customer = n; });
    $scope.$watch(function() { return PosReturnService.searchResults; }, function(n) { $scope.searchResults = n; });
    $scope.$watch(function() { return PosReturnService.showAll; }, function(n) { $scope.showAll = n; });

    $scope.$watch('search.string', function (newVal) {
        if (!newVal || newVal.trim() === "") {
            $scope.searchResults = [];
            $scope.showAll = false; // optional reset
        }
    });


    /*$scope.addCustomerModal = function () {
        if ($scope.totalProducts > 0) {
           $('#customerModal').modal('show');
        } else {
            $scope.showMessage('failed', 'Cannot proceed — no items added.', 6000);
        }
    };

    $scope.clearCustomerForm = function() {
        $scope.customer = {}; // reset customer model
        $scope.search = {
            string: ""
        };
        $scope.searchResults = [];
        if ($scope.customerForm) {
            $scope.customer.mobileNumber = "";
            $scope.customerForm.$setPristine();
            $scope.customerForm.$setUntouched();
        }
    }

    $scope.order = {
        customer: null,
    };
    $scope.customer = {}; // temporary modal object

    $scope.saveCustomer = function() {
        if ($scope.customerForm.$valid) {
            CustomerService.saveCustomer($scope.customer)
                .then(function(response) {
                    if (response.data.code === 200) {
                        $scope.order.customer = response.data.body; // saved customer
                        $('#customerModal').modal('hide');
                        $scope.customer = {};
                        $scope.customerForm.$setPristine();
                        $scope.customerForm.$setUntouched();
                    } else {
                        $scope.showMessage('failed', 'Error saving customer.', 6000);
                        console.error("Error saving customer:", response.data.message);
                    }
                })
                .catch(function(err) {
                    console.error("Error saving customer:", err);
                });
        }
    };

    $scope.customer = {};
    $scope.search = {
        string: ""
    };
    $scope.searchResults = [];
    $scope.showAll = false; // default: show only 3

    $scope.toggleShowAll = function() {
        console.log('Show All toggled:', $scope.showAll);
        $scope.showAll = !$scope.showAll;
    };

    // Search existing customers
    $scope.searchCustomer = function() {
        if ($scope.search.string) {
            CustomerService.search($scope.search.string).then(function(res) {
                $scope.searchResults = res.data.body || [];
                $scope.showAll = false; // reset to top 3 on new search
            });
        }
    };

    // Select a customer from search
    $scope.selectCustomer = function(cust) {
        $scope.customer = angular.copy(cust);
        $scope.order.customer = $scope.customer;
        $scope.searchResults = []; // hide list after selecting
        $('#customerModal').modal('hide');
    };*/

//POS return modal
    $scope.search = { txnNumber: "" };

    $scope.searchTransaction = function () {
        PosReturnService.fetchTransaction($scope.search.txnNumber)
            .then(function (transaction) {
                $scope.transaction = transaction;
            })
            .catch(function (err) {
                $rootScope.toastError(err);
            });
    };

    $scope.updateRefund = PosReturnService.updateRefund;

    $scope.getTotalRefund = PosReturnService.getTotalRefund;

    $scope.clearPosReturnForm = function() {
        PosReturnService.clearForm();
        $scope.transaction = null;
        $scope.search.txnNumber = "";
        if ($scope.returnForm) {
            $scope.returnForm.$setPristine();
            $scope.returnForm.$setUntouched();
        }
    };

    $scope.processReturn = function () {
        PosReturnService.processReturn(
            $scope.transaction,
            $scope.payment,
            $scope.discount
        ).then(function () {
            $scope.showMessage('success', 'Return successful!', 3500);
            $('#posReturnModal').modal('hide');
        }).catch(function (err) {
            $rootScope.toastError(err);
        });
    };

    // Keep controller transaction in sync with service
    $scope.$watch(function () { return PosReturnService.transaction; }, function(newVal) {
        $scope.transaction = newVal;
    });



    /*$scope.transaction = null;
    $scope.search = {
        txnNumber: ""
    };

    $scope.searchTransaction = function () {
        $scope.fetchTransaction($scope.search.txnNumber);
    };

    $scope.fetchTransaction = function (transactionId) {
        var req = Communication.request("GET", API.POS_GET_BY_TRANSACTION_ID + '/' + transactionId, {});
            req.then(function (resp) {
                log("POS transaction data fetch: " + JSON.stringify(resp));

                if (resp.code === 200) {
                    $scope.transaction = resp.body;
                }
            }, function (err) {
                log("POS transaction data fetch error", JSON.stringify(err));
            });
    };

    $scope.clearPosReturnForm = function() {
        $scope.transaction = null;
        $scope.search = {
            txnNumber: ""
        };
        if ($scope.returnForm) {
            $scope.returnForm.$setPristine();
            $scope.returnForm.$setUntouched();
        }
    }

    $scope.updateRefund = function (item) {
        if (item.returnQty > item.qty) {
            item.returnQty = item.qty; // safety check
        }
    };

    $scope.getTotalRefund = function () {
        if (!$scope.transaction) return 0;
        return $scope.transaction.items.reduce(function (sum, item) {
            var returnQty = parseFloat(item.returnQty) || 0;
            var price = parseFloat(item.price) || 0;
            return sum + (returnQty * price);
        }, 0);
    };

    $scope.returnMaster = {
        items: []
    };

    $scope.processReturn = function () {
        $scope.returnOrder($scope.transaction);

        Communication.request("POST", API.POS_SAVE, $scope.returnMaster)
            .then(function (resp) {
                if (resp.code === 200) {
                    $scope.returnMaster = {
                        items: []
                    };

                    $scope.showMessage('success', 'Return successful!', 3500);
                    // close modal
                    $('#posReturnModal').modal('hide');
                } else{
                    $rootScope.toastError(resp.message);
                }
            }, function (err) {
                log("POS return error", JSON.stringify(err));
                $rootScope.toastError(err.message);
            });
    };

    $scope.returnOrder = function(transaction) {
        if (!transaction || !transaction.items) return;

        $scope.returnMaster = {
            id: null,
            transactionId: null,
            transactionDate: new Date(),
            referenceNo: transaction.transactionId,
            transactionType: SALES_KEY.TRANSACTION_TYPE.POS,
            grossAmount: $scope.getTotalRefund(),
            netAmount: $scope.getTotalRefund(),
            discountType: $scope.discount.type,
            discountValue: 0,
            vatAmount: 0,
            totalAmount: $scope.getTotalRefund(),
            givenAmount: 0,
            returnAmount: 0,
            paymentType: $scope.payment.type,
            customerName: transaction.customerName,
            mobileNumber: transaction.mobileNumber,
            status: 'RETURNED'
        };

         $scope.returnMaster.items = transaction.items
            .filter(item => (parseFloat(item.returnQty) || 0) > 0)
            .map(item => ({
                itemMasterId: item.itemMasterId,
                itemCode: item.itemCode,
                itemName: item.itemName,
                quantity: parseFloat(item.returnQty),
                price: parseFloat(item.price),
                total: parseFloat(item.returnQty) * parseFloat(item.price)
            }));
    };*/
});