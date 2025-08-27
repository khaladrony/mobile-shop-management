app.service("OrderService", function($q, Communication) {
    var service = this;

    // State
    service.data = [];
    service.currentPage = 1;
    service.itemPerPage = 10;
    service.selectedOrder = null;
    service.order = {}; // to store currently selected order details

    // ----- DISCOUNT STATE -----
    service.discount = {
        type: SALES_KEY.DISCOUNT_TYPE.PERCENT,  // 'percent' or 'amount'
        value: 0
    };

    // Search filter
    service.searchFilter = {
        sortField: "transactionDate",
        sortDirection: "desc"
    };

    // Fetch paginated order data
    service.getDataList = function (page, size) {
        return Communication.request("POST", API.POS_FILTER + "?page=" + (page - 1) + "&size=" + size, service.searchFilter)
            .then(function(res) {
//                service.data = res.body || [];

                service.data.items = res.body.content;
                service.data.itemCount = res.body.totalElements;

                service.data.items.forEach(function(master) {
                    master.showDetails = false;
                });
                return service.data;
            });
    };

    // Toggle row details (expand/collapse)
    service.toggleDetails = function (order) {
        order.showDetails = !order.showDetails;
        return order.showDetails;
    };

    // Select an order (populate full details)
    service.selectOrder = function(master) {
        service.selectedOrder = angular.copy(master);

        // map it in a consistent object structure
        service.order = {
            id: master.id,
            transactionId: master.transactionId,
            transactionDate: master.transactionDate,
            grossAmount: master.grossAmount,
            netAmount: master.netAmount,
            discountType: master.discountType,
            discountValue: master.discountValue,
            vatAmount: master.vatAmount,
            totalAmount: master.totalAmount,
            givenAmount: master.givenAmount,
            paymentType: master.paymentType,
            orderItems: master.orderItems,
            totalProducts: master.totalProducts,
            customerName: master.customerName,
            mobileNumber: master.mobileNumber
        };

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

        service.order.orderItems = orderItems;
        service.order.totalProducts = orderItems.length;

        return service.order;
    };

    return service;
});
