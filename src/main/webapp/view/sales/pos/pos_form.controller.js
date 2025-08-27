app.controller('PosFormCtrl', function ($scope, $http, $state, $timeout,
                $q, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService,
                DialogBox, encrypt, Communication,$filter,growl, ItemService,
                ToasterMessageQueueService, DateHelperService, CustomerService,
                NotificationService, CustomerService, PaymentService, OrderService, ReturnService
                ) {

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

//  Toaster message
    $scope.messages = NotificationService.messages;

    $scope.showMessage = function(type, text, duration) {
        NotificationService.showMessage(type, text, duration);
    };

    $scope.closeMessage = function(i) {
        NotificationService.closeMessage(i);
    };


    // ---------- PAYMENT ----------
    $scope.payment = PaymentService.payment;
    $scope.discount = PaymentService.discount;

    $scope.openPaymentModal = function () {
        if ($scope.totalAmount > 0) {
            PaymentService.resetPayment();
            $('#paymentModal').modal('show');
        } else {
            $scope.showMessage('failed', 'No items added to checkout.', 6000);
        }
    };

    $scope.confirmPayment = function () {
        PaymentService.confirm($scope).then(function() {
            $('#paymentModal').modal('hide');
        });
    };

    $scope.holdOrder = function () {
        PaymentService.hold($scope);
    };

    $('#paymentModal').on('hidden.bs.modal', function () {
        $scope.$apply(function () {
            PaymentService.resetPayment();
        });
    });

    $scope.clearOrders = function() {
        $scope.id = null;
        PaymentService.resetPayment();
        PaymentService.resetDiscount();

        $scope.orderItems = [];
        $scope.calculateTotals();

        $scope.clearCustomerForm();
        CustomerService.order.customer = {};
        CustomerService.defaultCustomer();
    };

    // ---------- ORDERS ----------
    // Order state bindings
    $scope.data = OrderService.data;
    $scope.currentPage = OrderService.currentPage;
    $scope.itemPerPage = OrderService.itemPerPage;
    $scope.selectedOrder = OrderService.selectedOrder;

    // Load order list
    $scope.getDataList = function (page, size) {
        OrderService.getDataList(page, size).then(function(data) {
            $scope.data = data;
        });
    };

    // Toggle details
    $scope.toggleDetails = OrderService.toggleDetails;

    // Open modal
    $scope.openOrderModal = function () {
        $('#orderModal').modal('show');
        $scope.getDataList($scope.currentPage, $scope.itemPerPage);
    };

    // Select order
    $scope.selectOrder = function(master) {
        let order = OrderService.selectOrder(master);

        // Sync to scope
        angular.extend($scope, order);

        CustomerService.order.customer = {
            customerName: order.customerName,
            mobileNumber: order.mobileNumber
        };

        PaymentService.discount.type = order.discountType;
        PaymentService.discount.value = order.discountValue;

        $('#orderModal').modal('hide');
    };

    // ---------- CUSTOMER ----------
    $scope.customer = CustomerService.customer;
    $scope.search = CustomerService.search;
    $scope.searchResults = CustomerService.searchResults;
    $scope.showAll = CustomerService.showAll;
    $scope.order = CustomerService.order;

    $scope.addCustomerModal = function () {
        if ($scope.totalProducts > 0) {
            $('#customerModal').modal('show');
        } else {
            $scope.showMessage('failed', 'Cannot proceed — no items added.', 6000);
        }
    };

    $scope.clearCustomerForm = function(form) {
        CustomerService.clearCustomerForm(form);
    };

    $scope.clearCustomerResult = function() {
        CustomerService.searchResults = [];
    };

    $scope.saveCustomer = function(form) {
        if (form.$valid) {
            CustomerService.saveCustomer($scope.customer, form)
                .then(function(cust) {
                    $('#customerModal').modal('hide');
                    $scope.showMessage('success', 'Customer saved successfully', 3500);
                })
                .catch(function(err) {
                    $scope.showMessage('failed', err, 6000);
                });
        }
    };

    $('#customerModal').on('hidden.bs.modal', function () {
        $scope.$apply(function () {
            CustomerService.clearCustomerForm(form);
        });
    });

    $scope.searchCustomer = function() {
        CustomerService.searchCustomer($scope.search.string);
    };

    $scope.selectCustomer = function(cust) {
        CustomerService.selectCustomer(cust);
        $('#customerModal').modal('hide');
    };

    CustomerService.defaultCustomer();

    $scope.toggleShowAll = function() {
        $scope.showAll = CustomerService.toggleShowAll();
    };

    // Keep scope synced
    $scope.$watch(function() { return CustomerService.customer; }, function(n) { $scope.customer = n; });
    $scope.$watch(function() { return CustomerService.searchResults; }, function(n) { $scope.searchResults = n; });
    $scope.$watch(function() { return CustomerService.showAll; }, function(n) { $scope.showAll = n; });

    $scope.$watch('search.string', function (newVal) {
        if (!newVal || newVal.trim() === "") {
            $scope.searchResults = [];
            $scope.showAll = false;
        }
    });

    // ---------- RETURN ----------
    $scope.search = { txnNumber: "" };
//    $scope.remarks = "";
    $scope.transaction = ReturnService.transaction;

    // search
    $scope.searchTransaction = function () {
        ReturnService.fetchTransaction($scope.search.txnNumber)
            .then(function (transaction) {
                $scope.transaction = transaction;
            })
            .catch(function (err) {
                $rootScope.toastError(err);
            });
    };

    // update refund qty
    $scope.updateRefund = ReturnService.updateRefund;

    // calculate refund total
    $scope.getTotalRefund = ReturnService.getTotalRefund;

    // clear form
    $scope.clearPosReturnForm = function() {
        ReturnService.clearForm();
        $scope.transaction = null;
        $scope.search.txnNumber = "";
        $scope.remarks = "";
        if ($scope.returnForm) {
            $scope.returnForm.$setPristine();
            $scope.returnForm.$setUntouched();
        }
    };

    // process return
    $scope.processReturn = function () {
        ReturnService.processReturn(
            $scope.transaction,
            $scope.payment,
            $scope.discount,
            $scope.remarks
        ).then(function () {
            $scope.showMessage('success', 'Return successful!', 3500);
            $('#posReturnModal').modal('hide');
        }).catch(function (err) {
            $rootScope.toastError(err);
        });
    };

    // keep scope.transaction in sync
    $scope.$watch(function () {
        return ReturnService.transaction;
    }, function(newVal) {
        $scope.transaction = newVal;
    });


// Initialize
    $scope.calculateTotals();
    //    $scope.loadMenuItems();
});